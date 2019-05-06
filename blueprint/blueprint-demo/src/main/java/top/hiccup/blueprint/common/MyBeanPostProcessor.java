package top.hiccup.blueprint.common;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by wenhy on 2018/1/25.
 */
public class MyBeanPostProcessor implements BeanPostProcessor {

    /**
     * Bean后处理器：Spring提供的两个常用后处理器：BeanNameAutoProxyCreator  DefaultAdvisorAutoProxyCreator
     * 容器后处理器：负责处理容器本身：
     */

    // bean为当前正在初始化的Bean对象，beanName为当前正在初始化的Bean对象的ID
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("Step5：执行bean后处理器：<before>方法");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, String beanName) throws BeansException {
        System.out.println("Step8：执行bean后处理器：<after>方法");
        // JDK动态代理
        if("doSomeService".equals(beanName)){
            Object proxy = Proxy.newProxyInstance(bean.getClass().getClassLoader(),
                    bean.getClass().getInterfaces(),
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            Object invoke = method.invoke(bean, args);
                            if(null != invoke){
                                if(invoke instanceof String){
                                    return "大写："+((String)invoke).toUpperCase();
                                } else {
                                    return invoke;
                                }
                            } else {
                                return null;
                            }
                        }
                    });
            return proxy;
        }
        return bean;
    }

}
