package top.hiccup.servlet;

import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;

/**
 * 遵循servlet3.0规范的应用初始化器，可以替代web.xml动态的完成对listener，filter，servlet的注册和启动。
 *
 * 路径 META-INF/services/javax.servlet.ServletContainerInitializer
 * 必须放在jar包下，放在classpath的War中不会生效。
 *
 * 注意：WEB-INF/web.xml和WebApplicationInitializer不是相斥的。
 *      例如web.xml可以注册一个servlet，ServletContainerInitializer也可以注册另外一个servlet。
 *      甚至可以通过方法ServletContext#getServletRegistration(String)来修改web.xml中的注册信息。
 *      然而，若应用中出现web.xml，它的<web-app version>属性必须设置成3.0或者以上，否则ServletContainerInitializer将会在servlet容器启动时被忽略。
 *
 * @author wenhy
 * @date 2018/12/30
 */
@HandlesTypes({ConfigInitializer.class, CloseListener.class})
public class ApplicationInitializer implements ServletContainerInitializer {

    /**
     * 参数c是通过在ApplicationInitializer添加@HandlesTypes注解来完成配置的
     *
     * @param c
     * @param ctx
     * @throws ServletException
     */
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFF");
    }
}
