package top.hiccup.bridge.controller.base;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import net.sourceforge.stripes.controller.DispatcherServlet;
import net.sourceforge.stripes.controller.StripesFilter;

/**
 * StripesContext初始化
 *
 * @author wenhy
 * @date 2018/9/15
 */
public class StripesContextHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger(StripesContextHolder.class);

    private static volatile boolean hasInitialized = false;

    private static StripesFilter stripesFilter;

    private static DispatcherServlet dispatcherServlet;

    private static final ReentrantLock lock = new ReentrantLock();

    public static void init(ServletContext servletContext, ApplicationContext applicationContext) {
        // 借鉴单例模式的双检锁设计
        lock.lock();
        try {
            if (hasInitialized) {
                return;
            }
            LOGGER.info("StripesContext initialize begin");
            long startTime = System.currentTimeMillis();
            String rootContext = WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;
            // Maxwell mock时ServletContext中没有IOC容器
            if (null == servletContext.getAttribute(rootContext)) {
                servletContext.setAttribute(rootContext, applicationContext);
            }
            stripesFilter = new StripesFilter();
            StripesFilterConfig stripesFilterConfig = new StripesFilterConfig("StripesFilter", servletContext);
            stripesFilter.init(stripesFilterConfig);
            dispatcherServlet = new DispatcherServlet();
            StripesServletConfig stripesServletConfig = new StripesServletConfig("StripesDispatcher", servletContext);
            dispatcherServlet.init(stripesServletConfig);
            hasInitialized = true;
            LOGGER.info("StripesContext initialize completed: {}", System.currentTimeMillis() - startTime);
        } catch (Exception e) {
            LOGGER.error("StripesContext initialize fail: ", e);
            throw new RuntimeException("StripesContext initialize fail: ", e);
        } finally {
            lock.unlock();
        }
    }

    public static void doService(ServletContext servletContext, ApplicationContext applicationContext,
                                 ServletRequest httpServletRequest, ServletResponse httpServletResponse) throws IOException, ServletException {
        if (!hasInitialized) {
            init(servletContext, applicationContext);
        }
        stripesFilter.doFilter(httpServletRequest, httpServletResponse, new FilterChain() {
            @Override
            public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
                dispatcherServlet.service(servletRequest, servletResponse);
            }
        });
    }
}
