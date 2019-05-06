package top.hiccup.blueprint.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * Spring容器BeanFactoryHolder
 *
 * @author wenhy
 * @date 2018/1/16
 */
public final class BeanFactoryHolder implements BeanFactoryAware{

    private static BeanFactory beanFactory = null;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        BeanFactoryHolder.beanFactory = beanFactory;
    }

    public static BeanFactory getBeanFactory() {
        checkBeanFactory();
        return beanFactory;
    }

    private static void checkBeanFactory() {
        if(null == beanFactory) {
            throw new IllegalStateException("beanFactory not injected.");
        }
    }

}
