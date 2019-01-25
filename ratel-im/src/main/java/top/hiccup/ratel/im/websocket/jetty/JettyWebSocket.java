package top.hiccup.ratel.im.websocket.jetty;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;


/**
 * F
 *
 * @author wenhy
 * @date 2019/1/25
 */
public class JettyWebSocket implements WebSocketListener {

    /**
     * 用来记录当前在线连接数
     */
    private static volatile int onlineCount = 0;

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
     */
    private static Map<JettyWebSocket, JettyWebSocket> webSocketMap = new ConcurrentHashMap<JettyWebSocket, JettyWebSocket>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    @Override
    public void onWebSocketBinary(byte[] bytes, int i, int i1) {
        System.out.println("服务器收到二进制文件");
        //群发消息
        for (JettyWebSocket socket : webSocketMap.values()) {
            try {
                ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
                byteBuffer.put(bytes);
                socket.session.getRemote().sendBytes(byteBuffer);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    @Override
    public void onWebSocketClose(int i, String s) {
        // 在线数减1
        subOnlineCount();
        JettyWebSocket socket = webSocketMap.remove(this);
        System.out.println("客户端下线，当前在线人数：" + getOnlineCount());

        String message = "{\"message\":\"webSocket close!\"}";
        sendMessageToAll(message);
    }

    @Override
    public void onWebSocketConnect(Session session) {
        this.session = session;
        webSocketMap.put(this, this);
        addOnlineCount();
        System.out.println("客户端上线，当前在线人数：" + getOnlineCount());

        String message = "{\"message\":\"websocket open!\"}";
        sendMessageToAll(message);
    }

    @Override
    public void onWebSocketError(Throwable throwable) {

    }

    @Override
    public void onWebSocketText(String msg) {
        System.out.println("服务器收到消息：" + msg);
        // 群发消息
        for (JettyWebSocket item : webSocketMap.values()) {
            try {
                item.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    private void sendMessage(String message) throws IOException {
        this.session.getRemote().sendString(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    private void sendMessageToAll(String message) {
        for (JettyWebSocket item : webSocketMap.values()) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static synchronized int getOnlineCount() {
        return onlineCount;
    }

    private static synchronized void addOnlineCount() {
        JettyWebSocket.onlineCount++;
    }

    private static synchronized void subOnlineCount() {
        JettyWebSocket.onlineCount--;
    }
}
