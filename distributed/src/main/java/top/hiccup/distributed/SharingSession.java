package top.hiccup.distributed;

/**
 * 【分布式Session共享解决方案】
 *
 * 1、session复制：直接利用web容器的session复制功能，在集群中的几台服务器之间同步session对象，使得每台服务器上都保存所有的session信息，
 * 这样任何一台宕机都不会导致session的数据丢失，服务器使用session时，直接从本地获取。
 *
 * 2、session绑定：利用hash算法，比如nginx的ip_hash,使得同一个ip的请求分发到同一台服务器上。
 * 这种方式不符合对系统的高可用要求，因为一旦某台服务器宕机，那么该机器上的session也就不复存在了，用户请求切换到其他机器后么有session，无法完成业务处理。
 *
 * 3、session服务器：session服务器可以解决上面的所有的问题，利用独立部署的session服务器（集群）统一管理session，服务器每次读写session时，都访问session服务器。
 * 这种解决方案事实上是应用服务器的状态分离，分为无状态的应用服务器和有状态的session服务器，然后针对这两种服务器的不同特性分别设计架构。
 *
 * @author wenhy
 * @date 2019/1/21
 */
public class SharingSession {
}
