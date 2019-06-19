
## Redis基础知识
    Redis本质上是一个Key-Value类型的内存数据库，很像Memcached（但Redis支持持久化），
    整个数据库统加载在内存当中进行操作，定期通过异步操作把数据库数据（快照）flush到硬盘上进行保存（RDB或者操作日志AOF），
    因为是纯内存操作，Redis的性能非常出色，每秒10万次读写操作，是已知性能最快的Key-Value DB。

    Redis的出色之处不仅仅是性能，Redis最大的魅力是支持保存多种数据结构，此外单个value的最大限制是512M，
    而Memcached只能保存1MB的数据（Memcached目前还不能支持持久化，掉电会导致数据丢失）。
    
    因此Redis可以用来实现很多有用的功能，比方说用他的List来做FIFO双向链表，实现一个轻量级的高性能消息队列服务，用他的Set可以做高性能的tag系统等等。
    另外Redis也可以对存入的Key-Value设置expire时间，因此也可以被当作一个功能加强版的Memcached来用。

    Redis的主要缺点是数据库容量受到物理内存的限制，不能用作海量数据的高性能读写，因此Redis适合的场景主要局限在较小数据量的高性能操作和运算上（可以搭建分布式Redis集群）。

### Redis是单进程单线程的(单线程I/O)
    Redis利用队列技术将并发访问变为串行访问，消除了传统数据库串行控制的开销（锁和同步的开销）。

### Redis的回收策略
    volatile-lru：从已设置过期时间的数据集（server.db[i].expires）中挑选最近最少使用的数据淘汰
    volatile-ttl：从已设置过期时间的数据集（server.db[i].expires）中挑选将要过期的数据淘汰
    volatile-random：从已设置过期时间的数据集（server.db[i].expires）中任意选择数据淘汰
    allkeys-lru：从数据集（server.db[i].dict）中挑选最近最少使用的数据淘汰
    allkeys-random：从数据集（server.db[i].dict）中任意选择数据淘汰
    no-enviction（驱逐）：禁止驱逐数据

### MySQL里有2000w数据，Redis中只存20w的数据，如何保证Redis中的数据都是热点数据？

### Redis常见性能问题和解决方案
    (1) Master最好不要做任何持久化工作，如RDB内存快照和AOF日志文件
    (2) 如果数据比较重要，某个Slave开启AOF备份数据，策略设置为每秒同步一次
    (3) 为了主从复制的速度和连接的稳定性，Master和Slave最好在同一个局域网内
    (4) 尽量避免在压力很大的主库上增加从库
    (5) 主从复制不要用图状结构，用单向链表结构更为稳定，即：Master <- Slave1 <- Slave2 <- Slave3...
        这样的结构方便解决单点故障问题，实现Slave对Master的替换。如果Master挂了，可以立刻启用Slave1做Master，其他不变。

#### Memcache与Redis的区别都有哪些？
    1)存储方式
        Memecache把数据全部存在内存之中，断电后会挂掉，数据不能超过内存大小
        Redis有部份存在硬盘上，这样能保证数据的持久性
    2)数据支持类型
        Memcache对数据类型支持相对简单
        Redis有复杂的数据类型
    3)使用底层模型不同
        它们之间底层实现方式 以及与客户端之间通信的应用协议不一样
        Redis直接自己构建了VM机制，因为一般的系统调用系统函数的话，会浪费一定的时间去移动和请求
    4）value大小
        Redis最大可以达到1GB，而memcache只有1MB

#### Redis常见的性能问题都有哪些？如何解决？
    1)Master写内存快照，save命令调度rdbSave函数，会阻塞主线程的工作，当快照比较大时对性能影响是非常大的，会间断性暂停服务，所以Master最好不要写内存快照。
    2)MasterAOF持久化，如果不重写AOF文件，这个持久化方式对性能的影响是最小的，但是AOF文件会不断增大，AOF文件过大会影响Master重启的恢复速度。
      Master最好不要做任何持久化工作，包括内存快照和AOF日志文件，特别是不要启用内存快照做持久化,如果数据比较关键，某个Slave开启AOF备份数据，策略为每秒同步一次。
    3)Master调用BGREWRITEAOF重写AOF文件，AOF在重写的时候会占大量的CPU和内存资源，导致服务load过高，出现短暂服务暂停现象。
    4)Redis主从复制的性能问题，为了主从复制的速度和连接的稳定性，Slave和Master最好在同一个局域网内。

#### Cache Aside Pattern（旁路缓存设计模式）
    读： 先读缓存，未命中则读DB，再set缓存
    写： 先写DB，再del缓存（不是直接更新缓存：数据一致性问题）
    引申出CAP定理：Consistency(一致性)、 Availability(可用性)、Partition tolerance(分区容错性)
    

## Redis数据结构
   * string：简单动态字符串（Simple Dynamic String，SDS）
   * list：
          压缩列表（ziplist，先记录元素长度再记录元素数据，结构紧凑，节省内存，可以利用CPU缓存读特性），
                不能支持随机访问，单数据小于64Byte，列表元素个数小于512时使用
          双向循环链表
   * hash：
          压缩列表，hash中key和value都要小于64Byte，且键值对个数小于512时使用
          哈希表，哈希算法为MurmurHash2
   * set：
          有序数组，数据都是整数，元素个数小于512时使用
          哈希表（类似Java中的HashSet利用HashMap来实现）
   * zset：
          压缩列表，单数据小于64Byte，元素个数小于128时使用
          跳表+哈希表
```
struct sdshdr{
     // 记录buf数组中已使用字节的数量
     int len;
     // 记录buf数组中未使用字节的数量
     int free;
     // 字节数组，用于保存字符串（C语言中一个char占一个字节）
     char buf[];
}
```
持久化方式：
    1) 只将数据存储到磁盘，指针、数据结构不持久化，要恢复时再重新计算，例如hash结构中的哈希值
    2) 保留原有的存储格式，例如hash中的哈希表大小，每个数据被hash到的槽等信息都保存到磁盘
    
    Redis采用第一种，这样持久化方便但恢复的时候麻烦，类比JavaAPI的数据结构类也是采用第一次方式
    
    
