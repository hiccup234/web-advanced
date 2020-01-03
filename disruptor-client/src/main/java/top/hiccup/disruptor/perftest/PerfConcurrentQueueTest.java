package top.hiccup.disruptor.perftest;

import com.lmax.disruptor.EventFactory;
import top.hiccup.disruptor.common.MyEvent;
import top.hiccup.disruptor.common.MyEventFactory;

import java.util.concurrent.*;

/**
 * 并发队列性能测试
 *
 * @author wenhy
 * @date 2019/12/29
 */
public class PerfConcurrentQueueTest {

    private static long singleProducerTest() throws InterruptedException {
        // 无界并发队列
        ConcurrentLinkedQueue<MyEvent> concurrentLinkedQueue = new ConcurrentLinkedQueue<MyEvent>();
        CountDownLatch latch = new CountDownLatch(1);
        final long startTime = System.currentTimeMillis();
        // 新建消费者线程
        new Thread(() -> {
            // 因为是并发队列，如果取不到消息不会阻塞进程
            for (;;) {
                // 只取出不解析
                MyEvent myEvent = concurrentLinkedQueue.poll();
                if (myEvent != null && myEvent.getEventId() == Common.DATA_SIZE) {
                    long costTime = System.currentTimeMillis() - startTime;
                    System.out.println("JDK并发队列耗时：" + costTime);
                    long opsPerSecond = (Common.DATA_SIZE * 1000L) / (System.currentTimeMillis() - startTime);
                    System.out.println("JDK并发队列每秒吞吐量：" + opsPerSecond);
                    System.out.println();
                    latch.countDown();
                    break;
                }
            }
        }).start();

        for (int a = 0; a <= Common.DATA_SIZE; a++) {
            MyEvent myEvent = new MyEvent();
            myEvent.setEventId(a);
            concurrentLinkedQueue.offer(myEvent);
        }
        latch.await();
        return System.currentTimeMillis() - startTime;
    }

    private static volatile boolean stop = false;

    private static long mutliProducerTest() throws InterruptedException {
        // 创建事件对象工厂
        EventFactory<MyEvent> eventFactory = new MyEventFactory();
        // 无界并发队列
        ConcurrentLinkedQueue<MyEvent> concurrentLinkedQueue = new ConcurrentLinkedQueue<MyEvent>();
        ExecutorService executor = Common.getExecutorService();
        CountDownLatch latch = new CountDownLatch(1);
        long startTime = System.currentTimeMillis();
        new Thread(() -> {
            stop = false;
            while (!stop){
                executor.execute(() -> {
                    MyEvent myEvent = concurrentLinkedQueue.poll();
                    if (myEvent != null && myEvent.getEventId() == Common.DATA_SIZE) {
                        long costTime = System.currentTimeMillis() - startTime;
                        System.out.println("JDK并发队列耗时：" + costTime);
                        long opsPerSecond = (Common.DATA_SIZE * 1000L) / (System.currentTimeMillis() - startTime);
                        System.out.println("JDK并发队列每秒吞吐量：" + opsPerSecond);
                        System.out.println();
                        latch.countDown();
                        stop = true;
                    }
                });
            }
        }).start();

        for (long a = 0; a <= Common.DATA_SIZE; a++) {
            MyEvent myEvent = eventFactory.newInstance();
            myEvent.setEventId(a);
            executor.execute(() -> {
                concurrentLinkedQueue.offer(myEvent);
            });
        }
        latch.await();
        executor.shutdown();
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
            costTime += mutliProducerTest();
        }

        System.out.println("平均耗时：" + costTime / times);
        System.out.println("平均每秒吞吐量：" + (Common.DATA_SIZE * 1000L) / (costTime / times));
    }
}
