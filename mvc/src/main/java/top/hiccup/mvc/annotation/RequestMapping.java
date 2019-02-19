package top.hiccup.mvc.annotation;

import java.lang.annotation.*;

/**
 * 请求地址映射注解
 *
 * @author wenhy
 * @date 2018/8/22
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {

    String value();
}
