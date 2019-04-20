package top.hiccup.distributed;

/**
 * 【一致性Hash算法】
 * 一致性哈希算法在1997年由麻省理工学院的Karger等人在解决分布式Cache中提出的，设计目标是为了解决因特网中的热点(Hot spot)问题，初衷和CARP十分类似。
 *
 * 又称为两次哈希，先对服务器节点（一般以服务器界面名称如：shard_1,shard_2）做hash，形成一个0~2^32-1的哈希环，让服务器节点的hash值落在环上，
 * 再对要缓存的key做哈希，映射到哈希环上的服务器节点区间上，如果服务器节点发生扩容或宕机，新的节点加入只会分走原来区间的一部分走，其他的都不用重做哈希。
 *
 * 【哈希环偏斜】
 * 服务器节点比较少，可能导致哈希后所有节点在环上都挨得很近，形成环的偏斜，解决方法就是对服务器节点做虚拟节点，一台物理机器虚拟出成百上千台虚拟机器
 * （shard_1_node_1,shard_1_node_2,shard_2_node_1...），这样再对所有的虚拟节点做哈希，就可以保证能够均匀的分散再哈希环上了。
 *
 * 【代码实现】
 * 具体可以参考jedis包的Sharded类，initialize方法默认每个分片虚拟出160个节点，可以通过weight来控制（默认1）
 *                             getShardInfo方法利用了TreeMap的tailMap来查找哈希环上离当前key的hash值最近的节点，tailMap.firstKey()即是最近的
 *
 * @author wenhy
 * @date 2019/1/21
 */
public class ConsistentHashing {
}
