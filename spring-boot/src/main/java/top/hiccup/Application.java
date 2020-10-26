package top.hiccup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 服务启动引导类
 *
 * @author wenhy
 * @date 2019/6/25
 */
@SpringBootApplication
public class Application {

    /**
     * 服务启动入口方法
     * @param args
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        ctx.registerShutdownHook();
    }
}
