package top.hiccup.mvc.annotation;

import java.lang.annotation.*;

/**
 * 自定义Controller类
 *
 * @author wenhy
 * @date 2018/8/22
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {

    String value() default "";
}
