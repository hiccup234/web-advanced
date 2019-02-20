package top.hiccup.rpc.proxy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

import top.hiccup.rpc.common.ThreadPoolFactory;

/**
 * RPC请求服务
 *
 * @author wenhy
 * @date 2019/2/20
 */
public class RpcClientProxy {

    public void publish(Object service, int port) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                ThreadPoolFactory.getInstance().execute(() -> {
                    try {
                        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                        RpcRequest request = (RpcRequest) ois.readObject();

                        Object[] parameters = request.getParameters();
                        Class<?>[] paramTypes = new Class[parameters.length];
                        for (int i = 0; i < parameters.length; i++) {
                            paramTypes[i] = parameters[i].getClass();
                        }
                        Method method = service.getClass().getMethod(request.getMethodName(), paramTypes);
                        Object result = method.invoke(service, parameters);
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        oos.writeObject(result);
                        oos.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
