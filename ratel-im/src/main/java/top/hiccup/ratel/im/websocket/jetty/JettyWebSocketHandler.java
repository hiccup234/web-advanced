package top.hiccup.ratel.im.websocket.jetty;

import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

/**
 * 自定义ws处理器
 *
 * @author wenhy
 * @date 2019/1/25
 */
public class JettyWebSocketHandler extends WebSocketHandler {

    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        webSocketServletFactory.getPolicy().setIdleTimeout(10L * 60L * 1000L);
        webSocketServletFactory.getPolicy().setAsyncWriteTimeout(10L * 1000L);
        // 设置自定义的WebSocket组合
//        webSocketServletFactory.setCreator(new JettyWebSocketCreator());

        // 直接注册WebSocketListener
        webSocketServletFactory.register(JettyWebSocket.class);
    }
}
