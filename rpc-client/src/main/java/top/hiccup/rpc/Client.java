package top.hiccup.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

import top.hiccup.rpc.bean.User;
import top.hiccup.rpc.proxy.RpcRequest;
import top.hiccup.rpc.service.IUserService;

/**
 * 应用启动主类
 *
 * @author wenhy
 * @date 2019/2/20
 */
public class Client {

    private static String host = "127.0.0.1";
    private static int port = 8080;

    public static void main(String[] args) {
        System.out.println("客户端启动..");
        IUserService userService = (IUserService)Proxy.newProxyInstance(Client.class.getClassLoader(), new Class[]{IUserService.class}, new InvocationHandler(){
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                RpcRequest request = new RpcRequest("top.hiccup.rpc.service.impl.UserServiceImpl", method.getName(), args);
                Socket socket = null;
                Object result = null;
                try {
                    socket = new Socket(host, port);
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(request);
                    oos.flush();

                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    result = ois.readObject();
                    ois.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (socket != null) {
                        socket.close();
                    }
                }
                return result;
            }
        });
        User user = userService.getUserById(1001L);
        System.out.println(user);
    }
}
