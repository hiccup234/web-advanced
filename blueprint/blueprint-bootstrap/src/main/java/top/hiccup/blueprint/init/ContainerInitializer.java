package top.hiccup.blueprint.init;

import top.hiccup.blueprint.context.SpringContextHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

/**
 * ServletContainInitalizer是Java EE 6中Servlet 3.0的新增接口
 *
 * @author wenhy
 * @date 2018/1/26
 */
public class ContainerInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        // onStartup方法是一个Java Web应用中程序员可以控制的最早时间点
        // 不需要通过web.xml部署描述符来定义
        System.out.println("Servlet容器启动...");
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/root-context.xml");
        System.out.println(applicationContext == SpringContextHolder.getApplicationContext());
    }

}
