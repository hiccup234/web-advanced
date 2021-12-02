package top.hiccup.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 服务主入口
 *
 * @author wenhy
 * @date 2019/6/5
 */
@SpringBootApplication(scanBasePackageClasses = MainApplication.class)
public class MainApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainApplication.class);

    private Bootstrap bootstrap;

    public static void main(String[] args) {
        MainApplication main = new MainApplication();
        Runtime.getRuntime().addShutdownHook(new Thread(main::shutdown));
        try {
            main.startup();
        } catch (Throwable e) {
            LOGGER.error("Server startup failed", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 服务启动方法
     */
    private void startup() {
        bootstrap = new Bootstrap();
    }

    /**
     * 关机的钩子方法
     */
    private void shutdown() {
        LOGGER.warn("Server shutdown");
    }
}
