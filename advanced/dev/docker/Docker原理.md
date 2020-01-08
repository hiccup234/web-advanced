
## Docker原理

    计算单元，容器化，轻量级虚拟机环境（没有guest OS），利用了Linux 的 namespace、cgroups、chroot

    docker是lxc的管理器，lxc是cgroup的管理工具，cgroup是namespace的用户空间的管理接口。
    namespace是linux内核在task_struct中对进程组管理的基础机制。需要注意的是namespace隔离的系统资源是类似于PID号等内核资源，
    而非CPU 内存等实际物理资源。docker的物理资源限制和控制是通过Cgroup实现的。