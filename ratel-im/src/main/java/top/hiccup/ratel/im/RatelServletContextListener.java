package top.hiccup.ratel.im;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import top.hiccup.ratel.im.websocket.jetty.JettyServerBootStrap;

/**
 * ServletContext启动监听器
 *
 * @author wenhy
 * @date 2019/1/25
 */
public class RatelServletContextListener implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(RatelServletContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        JettyServerBootStrap.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.warn("服务器停机");
    }
}
