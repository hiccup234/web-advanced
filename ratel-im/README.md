#ratel-im

im: instant messenger

基于Netty的简易即时通讯工具

Q: JDK7 的NIO中 poll、epoll 存在空轮询的bug，是由于Linux Kernel 2.6.x某些版本，在socket.accept阻塞的时候，
如果连接突然中断，会往eventSet设置POLLUP或POLLERR，导致eventSet发生变化，使accept的线程被唤醒，则selectedKeys()返回空数组，造成空轮询

A: Netty和Jetty都采用了相同的解决策略，即重建一个selector，具体参见io.netty.channel.nio.NioEventLoop类
```
long currentTimeNanos = System.nanoTime();
for (;;) {
    selector.select(timeoutMillis);
    long time = System.nanoTime();
    if (time - TimeUnit.MILLISECONDS.toNanos(timeoutMillis) >= currentTimeNanos) {
        selectCnt = 1;
    } else if (SELECTOR_AUTO_REBUILD_THRESHOLD > 0 &&
            selectCnt >= SELECTOR_AUTO_REBUILD_THRESHOLD) {
        rebuildSelector();
        selector = this.selector;
        selector.selectNow();
        selectCnt = 1;
        break;
    }
    currentTimeNanos = time; 
    ...
 }
```
