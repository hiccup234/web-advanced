package top.hiccup.nosql.redis;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

/**
 * Redis客户端分片测试类
 *
 * @author wenhy
 * @date 2018/8/17
 */
public class ClientShardingTest {

    private static final String HOST = "127.0.0.1";
    private static final int PORT_1 = 6379;
    private static final int PORT_2 = 6380;
    private static final int PORT_3 = 6381;
    private static final int PORT_4 = 6382;

    public static void main(String[] args) {
        // Jedis默认配置
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setTestOnBorrow(true);
        // Redis分片列表
        List<JedisShardInfo> shardInfoList = new ArrayList<>();
        JedisShardInfo shardInfo1 = new JedisShardInfo(HOST, PORT_1);
        JedisShardInfo shardInfo2 = new JedisShardInfo(HOST, PORT_2);
        JedisShardInfo shardInfo3 = new JedisShardInfo(HOST, PORT_3);

        shardInfoList.add(shardInfo1);
        shardInfoList.add(shardInfo2);
        shardInfoList.add(shardInfo3);
        // 创建分片连接池
        ShardedJedisPool shardedJedisPool = new ShardedJedisPool(jedisPoolConfig, shardInfoList);

        // 实例化分片客户端 -- 实现一致性hash算法
        // 主要通过Sharded类中的initialize方法实现对分片节点的hash
        // 用TreeMap nodes来存储hash过后的分片信息（hash环上的虚节点与实际物理节点的映射关系）
        ShardedJedis shardedJedis = new ShardedJedis(shardInfoList);
        shardedJedis.setDataSource(shardedJedisPool);

        long startTime = System.currentTimeMillis();
        for(int i=0; i<100_000; i++) {
            shardedJedis.set(i+"key", "hahaha"+i);
        }
        System.out.println("总耗时：" + (System.currentTimeMillis() - startTime));


        JedisShardInfo shardInfo4 = new JedisShardInfo(HOST, PORT_4);
        shardInfoList.add(shardInfo4);
        ShardedJedis shardedJedis2 = new ShardedJedis(shardInfoList);
        for(int i=100_000; i<200_000; i++) {
            shardedJedis2.set(i+"key", "hahaha"+i);
        }

    }
}
