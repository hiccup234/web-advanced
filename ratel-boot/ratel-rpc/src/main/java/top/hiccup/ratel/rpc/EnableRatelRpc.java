package top.hiccup.ratel.rpc;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import top.hiccup.ratel.rpc.dubbo.apache.DubboApacheConfig;
import top.hiccup.ratel.rpc.jsf.JsfConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ratel-rpc
 *
 * @author wenhy
 * @date 2021/6/5
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ImportAutoConfiguration({DubboApacheConfig.class, JsfConfig.class})
public @interface EnableRatelRpc {
}
