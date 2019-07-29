
## 向中间件方向发展，立足PaaS，成为架构师

“云”是互联网的一个隐喻，“云计算”其实就是使用互联网来接入存储或者运行在远程服务器端的应用，数据，或者服务，云是分层的（IaaS==>PaaS==>SaaS）。

Software（软件）-as-a-Service
                    ^
Platform（平台）-as-a-Service
                     ^
Infrastructure（基础设施）-as-a-Service


## PaaS平台的常用技术

 * 容器：docker、garden
 
 * 服务编排：kubernetes、swarm、KVM
 
 * 分布式协同（分布式锁）：ZooKeeper（Apache）、Chubby（Google）
 
 * 持续集成：jenkins、maven、Devops
 
 * 日志框架：ElasticSearch+Logstash+Kinana（ELK）、SkyWalking、Zabbix
 
 * 负载均衡： F5、Nginx、Spring Cloud Ribbon
 
 * 消息队列：kafka、activeMQ、RabbitMQ、RocketMQ   
 
 * 实时计算：storm
 
 * Hadoop：实现了一个分布式文件系统（Hadoop Distributed File System），简称HDFS【高容错性】
    Hadoop框架最核心的设计：HDFS和MapReduce  
    HDFS为海量的数据提供了存储，则MapReduce为海量的数据提供了计算
    