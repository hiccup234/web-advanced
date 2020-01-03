package top.hiccup.disruptor.perftest;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 测试公用常量
 *
 * @author wenhy
 * @date 2019/9/12
 */
public class Common {

    /**
     * 数据量 600W
     */
    public static final int DATA_SIZE = 6_000_000;

    /**
     * 队列长度 = 32768
     */
    public static final int QUEUE_SIZE = 1 << 15;

    /**
     * 创建线程池
     * @return
     */
    public static ExecutorService getExecutorService() {
        return new ThreadPoolExecutor(2, 20, 60,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(1024),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
