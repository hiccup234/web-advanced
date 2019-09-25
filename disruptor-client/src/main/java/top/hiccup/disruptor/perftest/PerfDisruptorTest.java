package top.hiccup.disruptor.perftest;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import top.hiccup.disruptor.common.*;

import java.nio.ByteBuffer;
import java.util.concurrent.*;

/**
 * 线程间队列Disruptor性能测试类
 *
 * @author wenhy
 * @date 2019/9/12
 */
public class PerfDisruptorTest {

    public static long startTime;

    private static long singleProducerTest() throws InterruptedException {
        // 创建事件对象工厂
        EventFactory eventFactory = new MyEventFactory();
        // TODO 优化点：创建事件能不能通过拷贝的方式呢？如果每次都new一个对象肯定很慢（只是创建队列的时候慢）
        // TODO 单生产者其实性能更高（省去了线程切换）
        Disruptor<MyEvent> disruptor = new Disruptor<MyEvent>(eventFactory, Common.QUEUE_SIZE, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new YieldingWaitStrategy());
        CountDownLatch latch = new CountDownLatch(1);
        // 注册消费事件的处理器
        disruptor.handleEventsWith(new MyEventHandler(latch));
//        disruptor.handleEventsWithWorkerPool(new MyWorkHandler());
        // 启动Disruptor
        disruptor.start();
        startTime = System.currentTimeMillis();
        new Thread(() -> {
            RingBuffer<MyEvent> innerRingBuffer = disruptor.getRingBuffer();
            for (long a = 0; a <= Common.DATA_SIZE; a++) {
                long seq = innerRingBuffer.next();
                MyEvent myEvent = innerRingBuffer.get(seq);
                myEvent.setEventId(a);
                // 记住不要因为组装event太耗时而影响Disruptor测试的结果
//                myEvent.setEventName(a + "fff");
                innerRingBuffer.publish(seq);
            }
        }).start();
        latch.await();
        return  System.currentTimeMillis() - startTime;
    }

    public static void main(String[] args) throws Exception {
        long costTime = 0;
        for (int i = 0; i < 10; i++) {
            // 单线程写单线程读测试
            costTime += singleProducerTest();

            // 线程池写读测试
//        mutliThreadTest();
        }
        System.out.println("平均耗时：" + costTime / 10);
        System.out.println("平均每秒吞吐量：" + (Common.DATA_SIZE * 1000L) / (costTime / 10));
    }
}
