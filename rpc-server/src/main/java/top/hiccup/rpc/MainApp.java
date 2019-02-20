package top.hiccup.rpc;

import top.hiccup.rpc.proxy.RpcClientProxy;
import top.hiccup.rpc.service.IUserService;
import top.hiccup.rpc.service.impl.UserServiceImpl;

/**
 * 应用启动主类
 *
 * @author wenhy
 * @date 2019/2/20
 */
public class MainApp {

    public static void main(String[] args) {
        System.out.println("服务器端启动..");
        new Thread(()->{
            IUserService userService = new UserServiceImpl();
            RpcClientProxy proxy = new RpcClientProxy();
            proxy.publish(userService, 8080);
        }).start();
    }
}
