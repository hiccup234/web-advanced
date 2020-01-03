package top.hiccup.disruptor.perftest;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 加锁基准测试
 *
 * @author wenhy
 * @date 2019/12/29
 */
public class LockBenchmarkTest {

    /**
     * 5亿
     */
    private static final long MAX = 500_000_000L;

    public static void runIncrement() {
        long counter = 0;
        long start = System.currentTimeMillis();
        while (counter < MAX) {
            counter++;
            counter++;
        }
        long end = System.currentTimeMillis();
        System.out.println("Time spent is " + (end - start) + "ms without lock");
    }

    public static void runIncrementAtomic() {
        AtomicLong counter = new AtomicLong(0);
        long start = System.currentTimeMillis();
        while (counter.incrementAndGet() < MAX) {
        }
        long end = System.currentTimeMillis();
        System.out.println("Time spent is " + (end - start) + "ms with cas");
    }

    public static void runIncrementWithLock() {
        Lock lock = new ReentrantLock();
        long counter = 0;
        long start = System.currentTimeMillis();
        while (counter < MAX) {
            lock.lock();
            counter++;
            lock.unlock();
        }
        long end = System.currentTimeMillis();
        System.out.println("Time spent is " + (end - start) + "ms with lock");
    }

    public static void runIncrementWithSync() {
        long counter = 0;
        long start = System.currentTimeMillis();
        while (counter < MAX) {
            synchronized (LockBenchmarkTest.class) {
                counter++;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("Time spent is " + (end - start) + "ms with synchronized");
    }

    public static void main(String[] args) {
        runIncrement();
        runIncrementAtomic();
        runIncrementWithLock();
        runIncrementWithSync();
    }
}