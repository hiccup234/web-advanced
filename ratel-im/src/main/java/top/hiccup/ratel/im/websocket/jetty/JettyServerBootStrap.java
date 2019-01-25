package top.hiccup.ratel.im.websocket.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 嵌入式Jetty服务器启动类
 *
 * @author wenhy
 * @date 2019/1/25
 */
public class JettyServerBootStrap {

    private static final Logger LOGGER = LoggerFactory.getLogger(JettyServerBootStrap.class);

    private static final int PORT = 8234;

    public static void start() {
        Server server = new Server(PORT);
        ContextHandler context = new ContextHandler();
        context.setContextPath("/im");
        JettyWebSocketHandler im = new JettyWebSocketHandler();
        context.setHandler(im);
        server.setHandler(context);
        try {
            // 启动服务端
            server.start();
//            server.join();
        } catch (Exception e) {
            LOGGER.error("启动Jetty失败", e);
            e.printStackTrace();
        }
        LOGGER.info("主线程结束");
        System.out.println("主线程结束");
    }

    public static void main(String[] args) {
        start();
    }
}
