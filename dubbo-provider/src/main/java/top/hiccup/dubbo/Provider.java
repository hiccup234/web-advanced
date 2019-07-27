package top.hiccup.dubbo;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 服务提供者
 *
 * @author wenhy
 * @date 2019/7/26
 */
public class Provider {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("provider.xml");
        context.start();
        System.out.println("服务启动成功");
        // 阻塞main线程
        System.in.read();
        System.out.println("服务退出");
    }
}
