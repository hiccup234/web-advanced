package top.hiccup.blueprint.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 以静态变量保存Spring容器对象实例
 *
 * @author wenhy
 * @date 2018/1/26
 */
@Component(value="springContextHolder")
public final class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    public SpringContextHolder() {
        System.out.println("Spring注入容器本身实例对象");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 在类的内部可以直接访问private属性
        SpringContextHolder.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return applicationContext;
    }

    private static void checkApplicationContext() {
        if(null == applicationContext) {
            throw new IllegalStateException("applicationContext not injected.");
        }
    }

}
