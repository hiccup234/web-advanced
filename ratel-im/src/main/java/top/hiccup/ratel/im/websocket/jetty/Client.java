package top.hiccup.ratel.im.websocket.jetty;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

/**
 * F
 *
 * @author wenhy
 * @date 2019/1/25
 */
public class Client {

    public static void main(String args[]) {
        String destUri = "ws://127.0.0.1:7778/test/";
        if (args.length > 0) {
            destUri = args[0];
        }
        WebSocketClient client = new WebSocketClient();
        SimpleEchoSocket socket = new SimpleEchoSocket();
        try {
            client.start();
            URI echoUri = new URI(destUri);
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            request.setSubProtocols("c");
            request.setHeader("index", "3");
            /* 使用相应的webSocket进行连接 */
            client.connect(socket, echoUri, request);

            System.out.printf("Connecting to : %s%n", echoUri);
            socket.awaitClose(1000, TimeUnit.SECONDS);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                client.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
