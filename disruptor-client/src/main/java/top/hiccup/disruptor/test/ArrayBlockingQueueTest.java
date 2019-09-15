package top.hiccup.disruptor.test;

import com.lmax.disruptor.EventFactory;
import top.hiccup.disruptor.common.MyEvent;
import top.hiccup.disruptor.common.MyEventFactory;

import java.util.concurrent.*;

/**
 * ArrayBlockingQueue性能测试：
 * <p>
 * 1、阻塞队列容量为1024*1024
 * 2、线程池大小为100个线程
 *
 * @author wenhy
 * @date 2019/9/12
 */
public class ArrayBlockingQueueTest {

    private static Object lock = new Object();
    private static volatile boolean running = true;

    private static void singleTheradTest() {

    }

    private static void mutliThreadTest() throws InterruptedException {
        // 创建事件对象工厂
        EventFactory<MyEvent> eventFactory = new MyEventFactory();
        // 队列大小要与Disruptor的RingBuffer保持相同
        ArrayBlockingQueue<MyEvent> arrayBlockingQueue = new ArrayBlockingQueue<MyEvent>(Common.QUEUE_SIZE);
        ExecutorService executor = Common.getExecutorService();
        long startTime = System.currentTimeMillis();
        // 异步的通过线程池往队列里放事件，否则由于阻塞队列的拒绝策略会导致主线程被阻塞
        new Thread(() -> {
            for (long l = 0; l <= Common.DATA_SIZE; l++) {
                MyEvent myEvent = eventFactory.newInstance();
                myEvent.setEventId(l);
                executor.execute(() -> {
                    try {
                        arrayBlockingQueue.put(myEvent);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        }).start();
        for (long l = 0; l <= Common.DATA_SIZE; l++) {
            executor.execute(() -> {
                MyEvent myEvent = null;
                try {
                    //
                    myEvent = arrayBlockingQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (myEvent.getEventId() == Common.DATA_SIZE) {
                    synchronized (lock) {
                        running = false;
                        lock.notifyAll();
                    }
                }
            });
        }

        synchronized (lock) {
            while (running) {
                lock.wait();
            }
        }
        long costTime = System.currentTimeMillis() - startTime;
        System.out.println("花费时间：" + costTime);
        executor.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        // 单线程写单线程读测试
//        singleTheradTest();
        // 线程池写读测试
        mutliThreadTest();
    }
}
