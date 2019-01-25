package top.hiccup.ratel.im.websocket.jetty;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

/**
 * 注解处理器
 *
 * @author wenhy
 * @date 2019/1/25
 */
@WebSocket(maxTextMessageSize = 128 * 1024, maxBinaryMessageSize = 128 * 1024)
public class AnnotatedEchoSocket {

    @OnWebSocketConnect
    public void onWebSocketConnect(Session session) throws Exception {
        if (session.isOpen()) {
            session.getRemote().sendString("你好！");
            System.out.println("新连接============================================");
            Future<Void> future;
            future = session.getRemote().sendStringByFuture("Hello");
            try {
                future.get(2, TimeUnit.SECONDS);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                future = session.getRemote().sendStringByFuture(df.format(new Date()));
                future.get(2, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
        }
    }

    @OnWebSocketClose
    public void onWebSocketClose(int i, String string) {
        System.out.println("关闭ws连接");
    }

    @OnWebSocketMessage
    public void onWebSocketMessage(Session session, String msg) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            session.getRemote().sendString("你好！现在时间是：" + df.format(new Date()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("服务器收到消息：" + msg);
    }
}
