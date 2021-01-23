package top.hiccup.processer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

/**
 * BeanFactoryPostProcessor扩展
 *
 * @author wenhy
 * @date 2021/1/5
 */
public class DefaultBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        logger.info("通过registry，可以增加修改删除bean的定义");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        logger.info("可以干预BeanFactory的创建过程");
    }
}