package top.hiccup.blueprint.common;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * Created by wenhy on 2018/1/25.
 */
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    /**
     * 容器后处理器：可以对Spring容器进行自定义扩展
     * Spring提供的常用容器后处理器：
     *      1）PropertyPlaceholderConfigurer：属性占位符配置器（通过properties中的配置改写xml中配置的占位符${}）
     *      2）PropertyOverrideConfigurer：重写占位符配置器
     *      3）CustomScopeConfigurer：自动以自动装配配置器
     *      4）CustomAutowireConfigurer：自定义作用域配置器
     */


    // 回参为void，不能对Spring容器做替换
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("Spring容器后处理器...");
    }

}
