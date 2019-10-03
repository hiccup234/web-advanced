
## Java路书
Java基础：集合框架，反射，泛型，锁，多线程和Java线程模型，IO/Socket，RMI，EJB，JDBC，OSGI，JMM、JVM、类加载器、Java字节码技术

架构师TOGAF证书
掘金小册 -- Netty OK  \  极客时间  \  Java技术栈公众号 -- 年度总结

UML统一建模语言的常用图包括：用例图、静态图(包括类图、对象图和包图)、行为图、交互图(顺序图、合作图)、实现图。  C4模型：类似地图定位的缩放形势
    
### 2019进阶路书：
1、二刷《极客时间--组成原理》 -- 9.30前完成 -- 
2、二刷《深入理解JVM》 国庆前完成
3、二刷《极客时间--Java核心技术36讲》 + 《极客时间--深入拆解Java虚拟机》 9.30前完成

4、看完《鸟哥Linux私房菜》 国庆放假看
5、二刷《极客时间--数据结构与算法》 国庆放假看
6、Redis还要买本书看，MongoDB也要学习
7、Zookeeper + Dubbo 学习
8、Spring源码学习

二刷《高性能MySQL》 + 《极客时间--MySQL36讲》
看完《极客时间--计算机网络》 + 《极客时间--网络编程》 + 《极客时间--IM通讯》

二刷《极客时间--操作系统》

复习+精通 XML，Spring扩展时候用得到
复习 UML，学会画架构图，vivso等

### 技术输出
1、Disruptor高性能分析总结 -- 进行中
2、Kafka高性能原理总结
3、搭建工程脚手架 -- 完成
4、推行C4模型
5、推行技术分享
6、推行个人博客，技术文章




## 基础篇
### 基础知识
面向对象的特征？ -- 抽象，封装，继承，多态（RTTI，对象头类型指针）
final, finally, finalize 的区别？ -- finalize在GC时调用且只会调用一次
int 和 Integer 有什么区别？ -- 占用内存字节数，对象头
重载和重写的区别？ -- 构造器不能重写，通过super调用父类构造器
抽象类和接口有什么区别？ -- 接口default方法
说说反射的用途及实现？ -- 动态创建对象、访问字段、调用方法，采用委派模式实现=本地实现+动态实现，即JVM里C++和动态生成字节码
说说自定义注解的场景及实现？ -- @NoSSO @SPI
equals 与 == 的区别？ -- equals与hashCode
JDBC 流程？ -- 加载驱动，获取连接，prepareStatement，得到执行结果（由Class.forName引出SPI，再引出破坏双亲委派，线程上下文类加载器）
MVC 设计思想？ -- 模型、视图、控制器，随着前后端分离已经过时，目前流行RPC
HTTP 请求的 GET 与 POST 方式的区别？
session 与 cookie 区别？session 分布式处理？ -- Web容器间同步，数据库存储，专门的session服务
### 集合
List 和 Set 区别？List 和 Map 区别？ -- List线性表，Set不允许重复值，Map哈希表的一种实现
ArrayList 与 LinkedList 区别？ArrayList 与 Vector 区别？
HashMap 和 HashTable 的区别？HashSet 和 HashMap 区别？HashMap 和 ConcurrentHashMap 的区别？
HashMap 的工作原理及代码实现？ConcurrentHashMap 的工作原理及代码实现？
### 锁 && 线程 && JVM
创建线程的方式及实现？ -- 继承Thread，实现Runnable，实现Callable；但是Java中线程的表示只有一种，即Thread类
sleep()、wait()、join()、yield()有什么区别？ -- wait释放锁，sleep、yield不释放锁，join底层循环调用wait
说说 CountDownLatch 原理？说说 CyclicBarrier 原理？说说 Semaphore 原理？说说 Exchanger 原理？
说说 CountDownLatch 与 CyclicBarrier 区别？
讲讲线程池的实现原理？创建线程池的几种方式？线程的生命周期？线程池的生命周期？ -- 线程池本质HashSet，ThreadPoolExecutor，自定义参数
                                                                   每个Worker对应一个线程，增加了一个阻塞队列来，当没有任务的时候阻塞线程以及缓冲任务
ThreadLocal 原理分析？ThreadLocal什么时候会出现OOM的情况？ -- Thread类中ThreadLocalMap，key存储ThreadLocal对象(this)，线程池中没remove会OOM
说说线程安全问题？ -- 竞态条件、临界区，线程封闭，只读共享，对象安全共享，保护对象(锁)
volatile 实现原理？ -- 内存屏障，happens-before
synchronized 实现原理？ -- 1.6后的锁升级
synchronized 与 lock 的区别？ -- lock功能更全更灵活，tryLock，带超时lock，线程状态不一样：一个是BLOCKED，一个是WAITED
CAS 乐观锁？ -- X86架构CPU的cmpxchg指令
ABA 问题？ -- 版本号，AtomicStampedReference
乐观锁的业务场景及实现方式？ -- AQS，无锁化，数据库乐观锁

JVM内存模型，GC机制和原理？ -- JMM，内存一致性模型，happens-before，重排序，内存屏障
GC分哪两种？Minor GC 和Full GC有什么区别？什么时候会触发Full GC？分别采用什么算法？ -- 引用计数、可达性分析；标记-复制，标记-清除，标记-压缩-清除
StackOverflow异常有没有遇到过？⼀般你猜测会在什么情况下被触发？如何指定⼀个线程的堆栈⼤⼩？⼀般你们写多少？
 -- 栈内存溢出，一般递归过深或没有出口，参数个数过多，-Xss
 线上FullGC频繁，如何排查？ -- -XX:HeapDumpBeforeFullGC（https://blog.csdn.net/wilsonpeng3/article/details/70064336/）
### 性能优化
性能指标有哪些 -- QPS、TPS、PV、UV
如何发现性能瓶颈 -- IO/CPU，内存，GC，延迟
性能调优的常见手段 -- JVM调优，MySQL调优，缓存
说说你在项目中如何进行性能调优 -- ParNew+CMS，-Xms-Xmx大小相等，NewRatio=3；MySQL索引优化，数据结果集限制
你是否遇到过 CPU 100% 如何排查与解决？ -- 首先恢复线上服务，top -h，jstat -gcutil，jstack
你是否遇到过 内存 OOM 如何排查与解决？ -- 首先恢复线上服务，jps，jmap，mat
说说你对敏捷开发的实践？ -- Jira，Confluence
说说你对开发运维的实践DevOps？ -- 结合自己的阿里云ECS阐述
介绍下工作中的一个对自己最有价值的项目，以及在这个过程中的角色？ -- 桥接SpringMVC与Stripes，技术构想，验证，选型，开发，测试，上线
知道osgi吗？ 它是如何实现的？
请问你做过哪些JVM优化？使用什么方法达到什么效果？ -- JVM逆向调优，一切以节省内存为目的，不固定堆大小，newRatio=1，晋升Age设为30、60，然而一顿操作猛如虎
Class.forName("java.lang.String")和String.class.getClassLoader().loadClass("java.lang.String") 什么区别啊？
Tomcat的运行机制？分析Tomcat线程模型？Tomcat系统参数和调优？

## 核心篇
### 数据存储
MySQL 索引使用的注意事项？ -- 增加索引、索引覆盖、最左原则、索引前缀（like的前面不能放%）、索引条件下推
说说反模式设计？ -- 反范式设计，一致性和性能的综合考量
说说分库与分表设计？ -- MySQL的分区表，hash,range,group=hash+range
分库与分表带来的分布式困境与应对之策？ -- 由中间件层完成多表联合查询和join
说说 SQL 优化之道？ -- Schema>>索引>>SQL语句，exist 代替 in，union all 代替 union等，结合explain
MySQL 遇到的死锁问题？ -- 同一个事务多次获取不同的锁，RR比RC更容易产生死锁（因为由gap锁）
存储引擎的 InnoDB 与 MyISAM？ -- MyISAM不支持事务，不支持行锁，InnoDB的锁是加在索引上的，保存表的行数select * from快，支持全文检索，速度快性能好
数据库索引的原理？ -- B+树，哈希索引，聚簇索引
为什么要用B+tree？ -- 磁盘局部性原理，预读原理，为什么不用哈希索引，排序数组索引，平衡二叉树，红黑树？
聚集索引与非聚集索引的区别？ -- 类比书的传统目录和书侧面的便签目录
limit 20000 加载很慢怎么解决？ -- 先索引覆盖查出主键ID，然后再关联查询，以避免大量无意义的回表
选择合适的分布式主键方案？ -- 自增主键固定步长，雪花算法等
选择合适的数据存储方案？ -- Redis，MogoDB，RDBMS
聊聊 MongoDB 使用场景？ObjectId 规则？ -- 基于文件的key-valDB，采用JSON格式存储，Schema-free; time + machine + PID + inc类似雪花算法
倒排索引？ -- 通过单词映射所在文件
聊聊 ElasticSearch 使用场景？ -- 日志搜索，全文检索
### 缓存使用
Redis 有哪些数据类型？ -- string,list,hash,set,zset
Redis 内部结构？ -- 简单动态字符串sds，压缩表，跳跃表，哈希表
聊聊 Redis 使用场景？ -- 缓存为主，虽然支持持久化，单事务能力弱，所以一般只拿来做缓存；地理信息计算geo
Redis 持久化机制？如何实现持久化？ -- RDB，AOF，一般主节点不要开启持久化
Redis 集群方案与实现？ -- 客户端sharding，哨兵，Redis3.0官方Cluster
Redis 为什么是单线程的？ -- 单线程处理IO，NIO多路复用，避免同步开销和多线程切换
缓存穿透、缓存雪崩、缓存击穿 -- 一直不存在的key；大批量key同时失效；某个key失效时高并发
缓存降级
使用缓存的合理性问题 -- 缓存旁路设计
### 消息队列
消息队列的使用场景 -- 异步解耦，削锋填谷，看成线程池（线程池本身也有阻塞队列，只不过MQ是进程外的队列）
消息的重发补偿解决思路 -- 消费者幂等，RabbitMQ有消息重试机制
消息的幂等性解决思路 -- 分布式锁+本地事务
消息的堆积解决思路 -- 一般是消费者消费不过来引起堆积，
自己如何实现消息队列 -- 考虑优先级队列，延迟队列
如何保证消息的有序性？重复性？事务消息？ -- 集群中消息要求发到同一queue，M1先发收到ack后再发M2，消费端也是，先消费完M1再消费M2；
                                    重复性由消费者幂等；事务为先mq prepare再本地事务，然后commit，类似MySQL的binlog与redoLog的2PC

## 框架篇
### Spring
BeanFactory 和 ApplicationContext 有什么区别？ -- ApplicationContext = BeanFactory + Resource
Spring Bean 的生命周期 -- ApplicationContextAware，BeanPostProcessor，BeanFactoryPostProcessor，InitializeBean
Spring IOC 如何实现 -- 本质是一个ConcurrentHashMap，通过反射创建对象
说说 Spring AOP -- 动态代理的应用案例，JDK动态代理，CGLib代理
Spring AOP 实现原理
动态代理cglib 与 JDK
Spring 事务实现方式 -- 编程型事务（自己手动begin，commit），申明式事务（利用AOP实现，动态增强）
Spring 事务底层原理
如何自定义注解实现功能 -- 反射，通过Class、Feild、Method对象的getAnnotation
Spring MVC 运行流程 -- DispatchServlet到handlerAdapter再到handlerMapping再到Controller再到可能的视图解析器
Spring MVC 启动流程 -- handlerMapping
Spring 的单例实现原理 -- lazy init
Spring 框架中用到了哪些设计模式 -- 单例，工厂，代理，模板
Spring 其他产品Srping Boot、Spring Cloud、Spring Secuirity、Spring Data、Spring AMQP 等
### Netty
为什么选择 Netty -- 基于事件，高性能，源码规范清晰，定义编码模型，与Mina开发者同一人，Netty更新，开箱即用的组件
说说业务中，Netty 的使用场景 -- Spring 5.0的Reactor模型，自己处理I/O，替换Tomcat等
原生的 NIO 在 JDK 1.7 版本存在 epoll bug -- 
什么是TCP 粘包/拆包 -- TCP协议本身存在粘包/拆包问题
TCP粘包/拆包的解决办法 -- 1、固定长度 2、行分隔符 3、特定分隔符 4、报文头、报文体，头里面记录长度
Netty 线程模型 -- BIO为1：n模型，NIO为1：n或n：n
说说 Netty 的零拷贝 -- ByteBuf是利用了堆外的直接内存，这样就让OS有机会实现从内核态到用户态的零拷贝（直接映射）
Netty 内部执行流程 -- channel，channelPipline
Netty 重连实现 -- Client在connect的时候可以注册事件监听，会返回Future，可以判断连接成功否

## 分布式篇
### 微服务
前后端分离是如何做的 -- 各自独立部署，通过HTTP接口调用
微服务哪些框架 -- Spring Cloud，Dubbo，Sofa，美团的
你怎么理解 RPC 框架 -- 其实跟HTTP差不多，主要是自定义协议
说说 RPC 的实现原理 -- 底层基于Socket（TCP/IP）
说说 Dubbo 的实现原理 -- Provider，Registry，Consumer，Monitor
你怎么理解 RESTful
说说如何设计一个良好的 API
如何理解 RESTful API 的幂等性
如何保证接口的幂等性 -- 分布式锁+本地事务
说说 CAP 定理、 BASE 理论 -- CAP，基本可用+最终一致
怎么考虑数据一致性问题 -- 
说说最终一致性的实现方案 -- 核心原理：少数服从多少，Paxos、Raft、ZAB
你怎么看待微服务
微服务与 SOA 的区别
如何拆分服务 -- 按业务拆分
微服务如何进行数据库管理 -- 服务自有数据库，分库
如何应对微服务的链式调用异常 -- SkyWalking
微服务的安全
### 分布式
谈谈业务中使用分布式的场景
Session 分布式方案
分布式锁的场景
分布是锁的实现方案 -- 数据库，Redis，ZooKeeper
分布式事务 -- 2PC，TCC，本地消息表
集群与负载均衡的算法与实现
说说分库与分表设计 -- hash，range，group=hash+range
分库与分表带来的分布式困境与应对之策 -- 热点数据集中在某个分片，跨库join问题

## 安全&性能
### 安全问题
安全要素与 STRIDE 威胁
防范常见的 Web 攻击 -- 哈希碰撞攻击，服务不可用攻击（黑名单，限流）
服务端通信安全攻防
HTTPS 原理剖析 -- 最开始HTTP协议，建立SSL，协商对称加密的密钥，协商的过程是非对称加密的（将密钥给对方，防止中间人攻击，证书）
HTTPS 降级攻击
授权与认证


## 工程篇
### 需求分析
你如何对需求原型进行理解和拆分
说说你对功能性需求的理解
说说你对非功能性需求的理解
你针对产品提出哪些交互和改进意见？ -- 技术的角度，练习算法时考虑边界，H5收银台多次分享同时支付的问题
你如何理解用户痛点？ -- 最好的方法就是自己成为用户，举例：银行IT一般要到柜台实习一年
设计能力？大型网站技术架构

说说你在项目中使用过的 UML 图
你如何考虑组件化
你如何考虑服务化按业务拆分
你如何划分领域边界
说说你项目中的领域建模 -- DDD
说说概要设计
### 设计模式
你项目中有使用哪些设计模式？ -- 单例，工厂，代理，策略，门面，模板，构建器，装饰
说说常用开源框架中设计模式使用分析？ -- Spring中单例、工厂、代理、模板，MyBatis中代理，Lombok中构建器，JDK容器中装饰
说说你对设计原则的理解？ -- 单一职责，依赖倒置(IOC)，迪米特，开闭，里氏替换，接口隔离
23种设计模式的设计理念
设计模式之间的异同，例如策略模式与状态模式的区别 -- 策略本身封装了算法，将策略传入程序并直接执行即可，状态则是程序本身的逻辑转换
设计模式之间的结合，例如策略模式+简单工厂模式的实践
设计模式的性能，例如单例模式哪种性能更好？ -- 双检锁或静态内部类

你系统中的前后端分离是如何做的 -- MVC模式，前端H5
说说你的开发流程？ -- MRD，PRD，需求评审，设计评审，单元测试，代码review
你和团队是如何沟通的？ -- Jira，Confilunce
你如何进行代码评审？ -- reviewboard，代码讲解
说说你对技术与业务的理解基础？ -- 技术服务于业务，进阶：技术推动业务，高阶：技术产生业务
说说你在项目中遇到感觉最难Bug，怎么解决的？ -- 最难的都是线上不能复现的问题，加日志



### 面试提问
1、Java开发中用得最多的数据结构有哪些？
    ArrayList，HashMap
2、谈谈对HashMap的理解，底层数据结构，怎么解决Hash碰撞，HashMap是线程安全的吗？HashTable呢？JUC里的并发容器？
    哈希表的一种实现方式，底层数组+链表
3、谈谈JMM，说说类的加载过程，GC以及内存管理，平时在Tomcat里有没有配置过相关JVM参数，以及性能调优？
    重排序，volatile守护上下文，内存屏障；加载、链接（验证，准备，解析）、初始化
4、Http协议，Https呢？Get和Post的区别，Tcp/Ip协议，3次握手，4次挥手，以及滑动窗口机制？
    超文本传输协议，加密的超文本
5、开发中用了哪些数据库？存储引擎有哪些？悲观锁乐观锁的场景？
6、SpringMVC以及Mybatis实现原理，是否看过底层源码？
7、Redis中的数据类型，事务，使用场景？

### 蚂蚁金服面试问题
#### Java多线程
    线程池的原理，为什么要创建线程池？ -- 本质是一个HashSet，Java线程直接映射内核线程，创建一个需要1.4M，非常耗时耗资源
    线程的生命周期，什么时候会出现僵尸进程？ -- NEW，RUNNABLE，WAITING，TIMED_WAITING，BLOCKED，TERMILED；
                                        孤儿进程由1号托管，僵尸进程是因为父进程未处理子进程退出的SIGAL，导致无法释放pid等数据结构
    什么是线程安全，如何实现线程安全？ -- 线程封闭，只读共享，线程安全共享，加锁
    创建线程池有哪几个核心参数？ 如何合理配置线程池的大小？ -- IO密集型，CPU密集型
    synchronized、volatile区别、synchronized锁粒度、模拟死锁场景、原子性与可见性？ -- volatile可见性顺序性

#### JVM相关
    JVM内存模型，GC机制和原理，GC分哪两种？什么时候会触发Full GC？ -- 顺序一致性模型；引用计数，可达性分析；System.gc()，老年代满
    JVM里的有几种classloader，为什么会有多种？ -- BootStrap，Extension，APP；双亲委派模型
    什么是双亲委派机制？介绍一些运作过程，双亲委派模型的好处？
    什么情况下我们需要破坏双亲委派模型？ -- SPI
    常见的JVM调优方法有哪些？可以具体到调整哪个参数，调成什么值？
    JVM虚拟机内存划分、类加载器、垃圾收集算法、垃圾收集器、class文件结构是如何解析的？ -- 堆，栈，元数据，PC，直接内存；

#### Java扩展
    红黑树的实现原理和应用场景？ -- 近似平衡二叉树，快速搜索和范围查找
    NIO是什么？适用于何种场景？ -- 高并发，但单个请求数据量不大耗时短
    Java9比Java8改进了什么？ -- 默认G1，String底层不再采用char[]，模块化OSGI，AOT
    HashMap内部的数据结构是什么？底层是怎么实现的？ -- 哈希表，采用链式结构来解决哈希冲突
    说说反射的用途及实现，反射是不是很慢，我们在项目中是否要避免使用反射？ -- 委派实现先本地实现再动态生成字节码，默认阈值15
    说说自定义注解的场景及实现？ -- Controller上的@NoSSO
    List和Map区别，Arraylist与LinkedList区别，ArrayList与Vector区别？

#### Spring
    Spring AOP的实现原理和场景？应用场景很重要 -- 事务，日志，鉴权
    Spring bean的作用域和生命周期？
    Spring Boot比Spring做了哪些改进？ Spring 5比Spring4做了哪些改进？
    如何自定义一个Spring Boot Starter？
    Spring IOC是什么？优点是什么？
    SpringMVC、动态代理、反射、AOP原理、事务隔离级别？

#### 中间件
    Dubbo完整的一次调用链路介绍；
    Dubbo支持几种负载均衡策略？ -- 轮询，加权轮询，随机，加权随机，LRU
    Dubbo Provider服务提供者要控制执行并发请求上限，具体怎么做？ -- 限流，计数器&滑动窗口、漏斗、令牌桶
    Dubbo启动的时候支持几种配置方式？ -- xml，注解编程
    了解几种消息中间件产品？各产品的优缺点介绍？
    消息中间件如何保证消息的一致性和如何进行消息的重试机制？
    Spring Cloud熔断机制介绍？
    Spring Cloud对比下Dubbo，什么场景下该使用Spring Cloud？
    
#### 数据库篇
    锁机制介绍：行锁、表锁、排他锁、共享锁？悲观锁、乐观锁的业务场景及实现方式？
    事务介绍，分布式事务的理解，常见的解决方案有哪些，什么是两阶段提交、三阶段提交？类比binlog与redolog的两阶段提交
    MySQL记录binlog的方式主要包括三种模式？每种模式的优缺点是什么？
    分布式事务的原理2阶段提交，同步\异步\阻塞\非阻塞
    数据库事务隔离级别，MySQL默认的隔离级别-RR、Spring如何实现事务？声明式事务
    JDBC如何实现事务、嵌套事务实现、分布式事务实现？
    SQL的整个解析、执行过程原理、SQL行转列？
    乐观锁的业务场景及实现方式；
    MySQL记录binlog的方式主要包括三种模式？每种模式的优缺点是什么？ -- statement，row，混合模式
    MySQL锁，悲观锁、乐观锁、排它锁、共享锁、表级锁、行级锁；
    分布式事务的原理2阶段提交，同步\异步\阻塞\非阻塞；
    数据库事务隔离级别，MySQL默认的隔离级别、Spring如何实现事务、JDBC如何实现事务、嵌套事务实现、分布式事务实现；

#### Redis
    Redis为什么这么快？redis采用多线程会有哪些问题？ -- 单线程，NIO，浪费多核CPU，如果I/O采用多线程则需要做数据的同步，会影响并发度
    Redis支持哪几种数据结构？ -- string,list,set,zset,hash，GEO，HighperHighper
    Redis跳跃表的问题？ -- 查找复杂度可能会退化为O(n)，解决办法就是随机选择索引的层高
    Redis单进程单线程的Redis如何能够高并发？ -- 单线程仅是针对I/O操作来讲，省去了同步和线程切换的开销，NIO
    Redis如何使用Redis实现分布式锁？ -- setnx，还要注意最好加上expire失效时间，即超时锁
    Redis分布式锁操作的原子性，Redis内部是如何实现的？ -- 单线程I/O，天生具有串行同步性，带参数的set而不是setnx
    

如何进行JVM调优工作的？
调优在线上生产环境的机会比较少，只跟我们的架构师一起分析过2次线上服务挂掉的问题，另外就是自己本机实验调优：
1、服务502，CPU狂飙到1000%，首先恢复线上服务，然后再分析，通过jstack得到线程栈日志发现大量的tomcat http线程阻塞在log4j的callAppenders，
要获取RootLogger对象锁因为是同步方法，而最近加了一个APP端调用的接口，打了日志logger.info()，而这个接口访问量非常大，锁竞争激烈导致其他线程都阻塞挂起了，
解决办法就是去掉那个接口的日志打印并且给日志加上缓冲，或则可以考虑用线程异步打印日志。
2、服务502，CPU偏高，搜索日志有OOM，jmap -dump:live,format=b,file=test.bin Vmid拿到dump文件，MAT分析发现是ThreadLocal引起的内存泄漏，
线程池有ThreadLocal<LinkedList>没有remove导致的也是跟打印日志有关系。

单例：饥饿，懒汉，双检锁，静态内部类和枚举
工厂：Spring就是一个超级工厂，工厂模式下代码需要跟具体工厂耦合，抽象工厂更加解耦
门面：日志框架slf4j，定义接口interface
模板：JdbcTemplate，RedisTemplate，以及抽象类的默认实现也是模板模式
代理：JDK动态代理，CGLib等，用户实现Spring AOP
装饰：Collections的synchronized+List,Set,Map等方法，IO中的BufferSteam
构建器：lombok的@builder
策略：线程池的拒绝策略传入一段算法
命令：命令模式式传入一个参数，算法由内部已经实现好了

NoSQL与关系型数据库区别？
DB主要存储结构化数据，有表结构的定义，要符合第一二三范式
NoSQL主要存储非结构化数据，如key-value，按文件存储等

1、为什么JDBC的驱动类只能通过Class.forName()加载？一般加载类有三种方式
    a.通过new关键字的加载、连接、初始化【当前类加载器】
    b.Class.forName()【当前类加载器】
    c.this.getClass.getClassLoader.loadClass()【可以指定类加载器】
如果通过new来加载会导致程序必须要import特定的驱动类，这样的话耦合性太高，如果要更换数据库驱动就必须要修改程序
JDBC4.0已经不用依赖Class.forName，可以使用SPI来加载破坏双亲委派模型，线程上下文类加载器

2、(String)null是否合法？
因null值可以强制转换为任何Java类类型，所以(String)null是合法的

3、JDK中除了String外还有哪些类是final的？
除了String外，常见的还有基本类型的包装器：Byte,Boolean,Character,Short,Integer,Double等，以及：Class,Array,Field,Method,Constructor,Parameter 等等

4、Java 中应该使用什么数据类型来代表价格？	BigDecimal
    数据库设计中也常用bigint来表示价格从基本单位表示起，1元=100分
    
5、各版本HashMap变化对比，什么时候用LinkedHashMap，ConcurrentHashMap？

RPC通信原理，分布式通信原理

分布式寻址方式都有哪些算法？知道一致性hash吗？手写一下java实现代码？？你若userId取摸分片，那我要查一段连续时间里的数据怎么办？？？

如何解决分库分表主键问题，有什么实现方案？ -- 自增主键固定步长，雪花算法

redis和memcheched 什么区别为什么单线程的redis比多线程的memched效率要高啊？
redis有什么数据类型都在哪些场景下使用啊？

reids的主从复制是怎么实现的redis的集群模式是如何实现的呢redis的key是如何寻址的啊？

使用redis如何设计分布式锁？使用zk可以吗？如何实现啊这两种哪个效率更高啊？？

知道redis的持久化吗都有什么缺点优点啊？ 具体底层实现呢？

redis过期策略都有哪些LRU 写一下java版本的代码吧？？

说一下dubbo的实现过程注册中心挂了可以继续通信吗？？

dubbo支持哪些序列化协议？hessian 说一下hessian的数据结构PB知道吗为啥PB效率是最高的啊？？

知道netty吗'netty可以干嘛呀NIO,BIO,AIO 都是什么啊有什么区别啊？

dubbo复制均衡策略和高可用策略都有哪些啊动态代理策略呢？

为什么要进行系统拆分啊拆分不用dubbo可以吗'dubbo和thrift什么区别啊？

为什么使用消息队列啊消息队列有什么优点和缺点啊？

如何保证消息队列的高可用啊如何保证消息不被重复消费啊

kafka ，activemq,rabbitmq ，rocketmq都有什么优点，缺点啊？？？

如果让你写一个消息队列，该如何进行架构设计啊？说一下你的思路


http的工作流程？？ ？http1.0 http1.1http2.0 具体哪些区别啊？

TCP三次握手，四层挥手的工作流程画一下流程图，为什么不是四次五次或者二次啊？
画一下https的工作流程？具体如何实现？如何防止被抓包啊？

系统架构如何选择合适日志技术log4j、log4j2、slf4j、jcl…….

springAOP的原理，springAOP和Aspectj的关系，springAOP的源码问题

dubbo框架的底层通信原理

RPC通信原理，分布式通信原理

如何利用springCloud来架构微服务项目

如何正确使用docker技术

springMVC的底层原理、如何从源码来分析其原理

mybaits的底层实现原理，如何从源码来分析mybaits

mysql的索引原理，索引是怎么实现的

索引的底层算法、如何正确使用、优化索引

springboot如何快速构建系统

zk原理知道吗zk都可以干什么Paxos算法知道吗？说一下原理和实现？

如果让你写一个消息队列，该如何进行架构设计啊？说一下你的思路

分布式事务知道吗？ 你们怎么解决的？

1、Dubbo的超时重试？ -- retry
2、如何保障请求的执行顺序？
3、分布式事务与分布式锁？
4、分布式session管理？
5、Spring事务管理？
6、Mybatis如何分页？如何设置缓存？Mysql分页？
7、IO？NIO？阻塞与非阻塞的区别？
8、分布式接口的幂等性设计？不能重复扣款
9、JVM老年代与新生代的比例？ YGC与FGC发生在哪些场景？
10、Jstack,jmap,jutil分别的意义？如何线上排查JVM的相关问题？
11、线程池的构造器的5个参数及其具体意义？
12、单机上一个线程池正在处理请求，如果忽然断电会怎样？正在处理的任务和阻塞队列里的请求怎么处理？
13、快速排序？广度优先搜索队列实现
14、设计一个对外的接口，在1，2，3这三个主机上(IP不同)实现负载均衡和顺序轮询机制，需要考虑并发？


2、Java类加载器包括⼏种？它们之间的⽗⼦关系是怎么样的？双亲委派机制是什么意思？有什么好处？
启动Bootstrap类加载、扩展Extension类加载、系统System类加载。
父子关系如下：
启动类加载器 ，由C++ 实现，没有父类；
扩展类加载器，由Java语言实现，父类加载器为null；
系统类加载器，由Java语言实现，父类加载器为扩展类加载器；
自定义类加载器，父类加载器肯定为AppClassLoader。
双亲委派机制：类加载器收到类加载请求，自己不加载，向上委托给父类加载，父类加载不了，再自己加载。
优势避免Java核心API篡改。详细查看：深入理解Java类加载器(ClassLoader)
https://blog.csdn.net/javazejian/article/details/73413292/

3、如何⾃定义⼀个类加载器？你使⽤过哪些或者你在什么场景下需要⼀个⾃定义的类加载器吗？
自定义类加载的意义：
加载特定路径的class文件
加载一个加密的网络class文件
热部署加载class文件

6、做GC时，⼀个对象在内存各个Space中被移动的顺序是什么？
标记清除法，复制算法，标记整理、分代算法。
新生代一般采用复制算法 GC，老年代使用标记整理算法。
垃圾收集器：串行新生代收集器、串行老生代收集器、并行新生代收集器、并行老年代收集器。
CMSCurrent Mark Sweep收集器是一种以获取最短回收停顿时间为目标的收集器，它是一种并发收集器，采用的是Mark-Sweep算法。
详见 Java GC机制。
http://www.cnblogs.com/dolphin0520/p/3783345.htmll/

7、你有没有遇到过OutOfMemory问题？你是怎么来处理这个问题的？处理过程中有哪些收获？
permgen space、heap space 错误。
常见的原因：
内存加载的数据量太大：一次性从数据库取太多数据；
集合类中有对对象的引用，使用后未清空，GC不能进行回收；
代码中存在循环产生过多的重复对象；
启动参数堆内存值小。
http://outofmemory.cn/c/java-outOfMemoryError/


