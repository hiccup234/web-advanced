package top.hiccup.ratel.im.websocket.jetty;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

/**
 * F
 *
 * @author wenhy
 * @date 2019/1/25
 */
public class JettyWebSocketCreator implements WebSocketCreator {

    private AnnotatedEchoSocket annotatedEchoSocket;

    public JettyWebSocketCreator() {
        annotatedEchoSocket = new AnnotatedEchoSocket();
    }

    @Override
    public Object createWebSocket(ServletUpgradeRequest servletUpgradeRequest, ServletUpgradeResponse servletUpgradeResponse) {
        for (String sub : servletUpgradeRequest.getSubProtocols()) {
            /**
             *   官方的Demo，这里可以根据相应的参数做判断，使用什么样的websocket
             */
        }
        return annotatedEchoSocket;
    }
}
