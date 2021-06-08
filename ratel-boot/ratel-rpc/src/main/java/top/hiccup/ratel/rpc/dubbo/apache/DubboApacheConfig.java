package top.hiccup.ratel.rpc.dubbo.apache;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * ratel-rpc
 *
 * @author wenhy
 * @date 2021/6/5
 */
@Configuration
@EnableConfigurationProperties(DubboProperties.class)
@ConditionalOnProperty(name = "ratel.rpc.dubbo.apache.enable", havingValue = "true")
public class DubboApacheConfig {

}
