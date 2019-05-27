package top.hiccup.nosql.redis.cache;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 缓存失效问题
 *
 * @author wenhy
 * @date 2018/8/18
 */
public class CacheInvalid {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 6379;

    private static final int LOOP_TIMES = 100;

    private Jedis jedis = new Jedis(HOST, PORT);

    private AtomicInteger cacheCount = new AtomicInteger();
    private AtomicInteger dbCount = new AtomicInteger();
    private AtomicInteger nullCount = new AtomicInteger();

    public String get(String key) throws InterruptedException {
        // TODO 这里同一个jedis实例不能多线程访问，后面再查查原因
        Jedis jedis = new Jedis(HOST, PORT);
        String value = jedis.get(key);
        cacheCount.getAndIncrement();
        // 为null则表明缓存过期
        if (null == value) {
            nullCount.getAndIncrement();
            // 设置3min的超时时间，防止del操作失败的时候，下次缓存过期一直不能load db
            String mutexKey = "$mutex_"+key;
            if (jedis.setnx(mutexKey, "1") == 1) {
                // 设置锁失效时间，防止del失败的情况下锁一直被占用
                jedis.expire(mutexKey, 2 * 60);
                // 从DB获取值
                value = "haha";
                dbCount.getAndIncrement();
                jedis.set(key, value);
                jedis.expire(key, 5 * 60);
                jedis.del(mutexKey);
                return value;
            } else {
                Thread.sleep(50);
                // 递归调用进行重试
                return get(key);
            }
        } else {
            return value;
        }
    }

    /**
     * 永不过期
     * @param args
     */
//    String get(final String key) {
//        V v = redis.get(key);
//        String value = v.getValue();
//        long timeout = v.getTimeout();
//        if (v.timeout <= System.currentTimeMillis()) {
//            // 异步更新后台异常执行
//            threadPool.execute(new Runnable() {
//                public void run() {
//                    String keyMutex = "mutex:" + key;
//                    if (redis.setnx(keyMutex, "1")) {
//                        // 3 min timeout to avoid mutex holder crash
//                        redis.expire(keyMutex, 3 * 60);
//                        String dbValue = db.get(key);
//                        redis.set(key, dbValue);
//                        redis.delete(keyMutex);
//                    }
//                }
//            });
//        }
//        return value;
//    }

    public static void main(String[] args) {
        final CacheInvalid main = new CacheInvalid();
        List<Thread> threads = new ArrayList<>(LOOP_TIMES);
        final CyclicBarrier barrier = new CyclicBarrier(LOOP_TIMES);
        for(int i=0; i<LOOP_TIMES; i++) {
            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("阻塞前");
                        barrier.await();
                        System.out.println("阻塞后");
                        System.out.println(main.get("key222"));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }));
        }
        for(int i=0; i<LOOP_TIMES; i++) {
            threads.get(i).start();
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(main.cacheCount);
        System.out.println(main.dbCount);
        System.out.println(main.nullCount);
    }

}
