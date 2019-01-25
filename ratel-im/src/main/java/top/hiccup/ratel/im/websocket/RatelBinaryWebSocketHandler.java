package top.hiccup.ratel.im.websocket;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

/**
 * F
 *
 * @author wenhy
 * @date 2019/1/25
 */
public class RatelBinaryWebSocketHandler extends BinaryWebSocketHandler {

    @Override
    public void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        // ...
    }
}
