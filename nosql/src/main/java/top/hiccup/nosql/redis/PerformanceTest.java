package top.hiccup.nosql.redis;

import redis.clients.jedis.Jedis;

/**
 * 单台Redis性能测试
 *
 * @author wenhy
 * @date 2018/8/17
 */
public class PerformanceTest {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 6379;

    private static final int LOOP_TIMES = 100_000;

    public static void main(String[] args) {
        Jedis jedis = new Jedis(HOST, PORT);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < LOOP_TIMES; i++) {
            jedis.set("key" + i, "test");
        }
        long endTime = System.currentTimeMillis();
        System.out.println("总耗时：" + (endTime - startTime));
        System.out.println("string类型IO性能：" + LOOP_TIMES * 1.0 / (endTime - startTime) * 1000);
    }
}
