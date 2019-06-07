package top.hiccup.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务主入口
 *
 * @author wenhy
 * @date 2019/6/5
 */
public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private Bootstrap bootstrap;

    public static void main(String[] args) {
        Main main = new Main();
        Runtime.getRuntime().addShutdownHook(new Thread(main::shutdown));
        try {

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
