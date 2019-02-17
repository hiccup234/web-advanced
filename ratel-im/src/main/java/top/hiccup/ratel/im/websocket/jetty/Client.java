package top.hiccup.ratel.im.websocket.jetty;

import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.StatusCode;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

/**
 * 简单客户端示例
 *
 * @author wenhy
 * @date 2019/1/25
 */
public class Client {

    public static void main(String args[]) {
        String uriStr = "ws://127.0.0.1:8234/im/";
        WebSocketClient client = new WebSocketClient();
        SimpleEchoSocket socket = new SimpleEchoSocket();
        try {
            client.start();
            URI uri = new URI(uriStr);
            ClientUpgradeRequest request = new ClientUpgradeRequest();

            request.setSubProtocols("c");

            request.setHeader("index", "3");
            System.out.println("正在连接到：" + uriStr);
            client.connect(socket, uri, request);
//            socket.awaitClose(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                client.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


@WebSocket(maxTextMessageSize = 64 * 1024)
class SimpleEchoSocket {

    private Session session;

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.out.printf("连接关闭: %d - %s%n", statusCode, reason);
        this.session = null;
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.printf("客户端连接: %s%n", session);
        this.session = session;
        while (true) {
            try {
                Future<Void> fut;
                fut = session.getRemote().sendStringByFuture("你好服务器");
                fut.get(2, TimeUnit.SECONDS);

                fut = session.getRemote().sendStringByFuture("你好服务器2");
                fut.get(2, TimeUnit.SECONDS);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        session.close(StatusCode.NORMAL, "关闭连接");
    }

    @OnWebSocketMessage
    public void onMessage(String msg) {
        System.out.printf("接收到服务器消息: %s%n", msg);
    }
}