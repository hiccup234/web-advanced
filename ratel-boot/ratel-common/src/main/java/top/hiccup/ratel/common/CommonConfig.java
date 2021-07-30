package top.hiccup.ratel.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import top.hiccup.ratel.common.spring.config.CommonProperties;

/**
 * 应用上下文配置
 *
 * @author wenhy
 * @date 2021/6/7
 */
@Slf4j
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableConfigurationProperties(CommonProperties.class)
public class CommonConfig implements InitializingBean, DisposableBean {

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
