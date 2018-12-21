package top.hiccup.nosql.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * Redis服务端Cluster集群模式测试类
 *
 * @author wenhy
 * @date 2018/8/19
 */
public class ClusterTest {
    private HostAndPort cluster1 = new HostAndPort("127.0.0.1", 6379);

    private JedisCluster jedisCluster = new JedisCluster(cluster1);
}
