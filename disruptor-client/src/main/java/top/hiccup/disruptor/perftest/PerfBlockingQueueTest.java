package top.hiccup.disruptor.perftest;

import com.lmax.disruptor.EventFactory;
import top.hiccup.disruptor.common.MyEvent;
import top.hiccup.disruptor.common.MyEventFactory;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * JDK阻塞队列性能测试：
 *
 * @author wenhy
 * @date 2019/9/12
 */
public class PerfBlockingQueueTest {

    private static long singleProducerTest() throws InterruptedException {
        // 队列大小要与Disruptor的RingBuffer保持相同
        final BlockingQueue<MyEvent> blockingQueue = new ArrayBlockingQueue<>(Common.QUEUE_SIZE);
//        LinkedBlockingQueue<MyEvent> blockingQueue = new LinkedBlockingQueue<MyEvent>(Common.QUEUE_SIZE);
        CountDownLatch latch = new CountDownLatch(1);
        final long startTime = System.currentTimeMillis();

        new Thread(() -> {
            for (int a = 0; a <= Common.DATA_SIZE; a++) {
                try {
                    // 只取出不消費
                    MyEvent myEvent = blockingQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            long costTime = System.currentTimeMillis() - startTime;
            System.out.println("单线程生产者耗时：" + costTime);
            long opsPerSecond = (Common.DATA_SIZE * 1000L) / (System.currentTimeMillis() - startTime);
            System.out.println("每秒吞吐量：" + opsPerSecond);
            System.out.println();
            latch.countDown();
        }).start();

        for (int a = 0; a <= Common.DATA_SIZE; a++) {
            MyEvent myEvent = new MyEvent();
            myEvent.setEventId(a);
            blockingQueue.put(myEvent);
        }
        latch.await();
        return System.currentTimeMillis() - startTime;
    }

    private static void mutliProducerTest() throws InterruptedException {
        // 创建事件对象工厂
        EventFactory<MyEvent> eventFactory = new MyEventFactory();
        // 队列大小要与Disruptor的RingBuffer保持相同
        ArrayBlockingQueue<MyEvent> arrayBlockingQueue = new ArrayBlockingQueue<MyEvent>(Common.QUEUE_SIZE);
        ExecutorService executor = Common.getExecutorService();
        // 异步的通过线程池往队列里放事件，否则由于阻塞队列的拒绝策略会导致主线程被阻塞
        long startTime = System.currentTimeMillis();
        new Thread(() -> {
            for (long a = 0; a <= Common.DATA_SIZE; a++) {
                MyEvent myEvent = eventFactory.newInstance();
                myEvent.setEventId(a);
                executor.execute(() -> {
                    try {
                        arrayBlockingQueue.put(myEvent);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        }).start();
        for (long a = 0; a <= Common.DATA_SIZE; a++) {
            executor.execute(() -> {
                try {
                    MyEvent myEvent = arrayBlockingQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        long costTime = System.currentTimeMillis() - startTime;
        System.out.println("多线程生产者：" + costTime);
        executor.shutdown();
    }

    public static void main(String[] args) throws InterruptedException, IOException {
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
