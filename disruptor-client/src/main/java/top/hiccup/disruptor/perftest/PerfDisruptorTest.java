package top.hiccup.disruptor.perftest;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import top.hiccup.disruptor.common.*;

import java.nio.ByteBuffer;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * 线程间队列Disruptor性能测试类
 *
 * @author wenhy
 * @date 2019/9/12
 */
public class PerfDisruptorTest {

    public static long startTime;

    private static long singleProducerTest() throws InterruptedException {
        // 创建事件工厂
        EventFactory eventFactory = new MyEventFactory();
        Disruptor<MyEvent> disruptor = new Disruptor<>(eventFactory, Common.QUEUE_SIZE,
                DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new YieldingWaitStrategy());
        // 线程间计数器
        CountDownLatch latch = new CountDownLatch(1);
        // 注册消费事件的处理器
        disruptor.handleEventsWith(new MyEventHandler(latch));
        // 启动Disruptor
        disruptor.start();

        startTime = System.currentTimeMillis();
        // 生产消息
        new Thread(() -> {
            RingBuffer<MyEvent> innerRingBuffer = disruptor.getRingBuffer();
            for (long a = 0; a <= Common.DATA_SIZE; a++) {
                long seq = innerRingBuffer.next();
                try {
                    MyEvent myEvent = innerRingBuffer.get(seq);
                    myEvent.setEventId(a);
                    // TODO 记住不要因为组装event太耗时而影响Disruptor测试的结果
//                myEvent.setEventName(a + "fff");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    innerRingBuffer.publish(seq);
                }
            }
        }).start();

        // 阻塞线程等待消息被消费完
        latch.await();
        return System.currentTimeMillis() - startTime;
    }

    private static long mutliProducerTest() throws InterruptedException {
        // 创建事件工厂
        EventFactory eventFactory = new MyEventFactory();
        Disruptor<MyEvent> disruptor = new Disruptor<>(eventFactory, Common.QUEUE_SIZE,
                DaemonThreadFactory.INSTANCE, ProducerType.MULTI, new YieldingWaitStrategy());
        // 线程间计数器
        CountDownLatch latch = new CountDownLatch(1);
        // 注册消费事件的处理器
        disruptor.handleEventsWith(new MyEventHandler(latch));
        // 启动Disruptor
        disruptor.start();

        startTime = System.currentTimeMillis();
        ExecutorService executor = Common.getExecutorService();
        // 生产消息
        new Thread(() -> {
            LongAdder longAdder = new LongAdder();
            longAdder.reset();
            RingBuffer<MyEvent> innerRingBuffer = disruptor.getRingBuffer();
            for (long a = 0; a <= Common.DATA_SIZE; a++) {
                executor.execute(() -> {
                    long seq = innerRingBuffer.next();
                    MyEvent myEvent = innerRingBuffer.get(seq);
                    myEvent.setEventId(0);
                    innerRingBuffer.publish(seq);
                    longAdder.increment();
                });
            }
            while (longAdder.longValue() < Common.DATA_SIZE) {
                Thread.yield();
            }
            // 在队列最后写入Common.DATA_SIZE，这样保证消费者在队列最后消费的Common.DATA_SIZE，然后唤醒主线程
            long seq = innerRingBuffer.next();
            try {
                MyEvent myEvent = innerRingBuffer.get(seq);
                myEvent.setEventId(Common.DATA_SIZE);
            } finally {
                innerRingBuffer.publish(seq);
            }
        }).start();
        // 阻塞线程等待消息被消费完
        latch.await();
        // 记得关闭线程池和disruptor
        executor.shutdown();
        disruptor.shutdown();
        return System.currentTimeMillis() - startTime;
    }

    // 多线程一定会变慢吗？？
    private static long mutliProducerTest2() throws InterruptedException {
        // 创建事件工厂
        EventFactory eventFactory = new MyEventFactory();
        Disruptor<MyEvent> disruptor = new Disruptor<>(eventFactory, Common.QUEUE_SIZE,
                DaemonThreadFactory.INSTANCE, ProducerType.MULTI, new YieldingWaitStrategy());
        // 线程间计数器
        CountDownLatch latch = new CountDownLatch(1);
        // 注册消费事件的处理器
        disruptor.handleEventsWith(new MyEventHandler(latch));
        // 启动Disruptor
        disruptor.start();

        startTime = System.currentTimeMillis();
        ExecutorService executor = Common.getExecutorService();
        // 生产消息
        new Thread(() -> {
            LongAdder longAdder = new LongAdder();
            longAdder.reset();
            RingBuffer<MyEvent> innerRingBuffer = disruptor.getRingBuffer();
            for (long a = 0; a <= Common.DATA_SIZE; a+=50) {
                executor.execute(() -> {
                    long seq = innerRingBuffer.next(50);
                    // TODO 这里的49很有讲究
                    for (int i = 49; i >= 0; i--) {
                        MyEvent myEvent = innerRingBuffer.get(seq - i);
                        myEvent.setEventId(0);
                        longAdder.increment();
//                        innerRingBuffer.publish(seq - i);
                    }
                    innerRingBuffer.publish(seq-49, seq);
                });
            }
            while (longAdder.longValue() < Common.DATA_SIZE - 50 ) {
                Thread.yield();
            }
            // 在队列最后写入Common.DATA_SIZE，这样保证消费者在队列最后消费的Common.DATA_SIZE，然后唤醒主线程
            long seq = innerRingBuffer.next();
            try {
                MyEvent myEvent = innerRingBuffer.get(seq);
                myEvent.setEventId(Common.DATA_SIZE);
            } finally {
                innerRingBuffer.publish(seq);
            }
        }).start();
        // 阻塞线程等待消息被消费完
        latch.await();
        executor.shutdown();
        disruptor.shutdown();
        return System.currentTimeMillis() - startTime;
    }

    public static void main(String[] args) throws Exception {
        // 测试次数
        int times = 10;
        long costTime = 0;
        for (int i = 0; i < times; i++) {
            // 单线程写单线程读测试
//            costTime += singleProducerTest();

            // 线程池写读测试
//            costTime += mutliProducerTest();

            costTime += mutliProducerTest2();
        }

        System.out.println("平均耗时：" + costTime / times);
        System.out.println("平均每秒吞吐量：" + (Common.DATA_SIZE * 1000L) / (costTime / times));
    }
}
