package top.hiccup.boot.core.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import top.hiccup.boot.common.Callback;

/**
 * 基于Netty的网络服务器
 *
 * @author wenhy
 * @date 2019/6/5
 */
public class NettyServer extends AbstractServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServer.class);

    private final ServerBootstrap serverBootstrap;

    private final ChannelGroup channelGroup;

    public NettyServer(EventLoopGroup bossGroup, EventLoopGroup workerGroup) {
        serverBootstrap = BootstrapHelper.serverBootstrap(bossGroup, workerGroup);
        channelGroup = null;





    }

    @Override
    public void shutdown(Callback<Void> callback) {
        LOGGER.warn("NettyServer shutdown");
    }
}

class BootstrapHelper {
    private BootstrapHelper() {}

    private static final boolean USE_EPOLL = "linux".equalsIgnoreCase(System.getProperty("os.name"));

    public static ServerBootstrap serverBootstrap(EventLoopGroup bossGroup, EventLoopGroup workerGroup) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        return serverBootstrap.group(bossGroup, workerGroup)
                .childOption(ChannelOption.TCP_NODELAY, true)
                // 一分钟超时
                .childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, 60000)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .channel(USE_EPOLL ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                // 表示系统用于临时存放已完成三次握手的请求的队列的最大长度，如果连接建立频繁，服务器处理创建新连接较慢，可以适当调大这个参数
                .option(ChannelOption.SO_BACKLOG, 1024);
    }
}