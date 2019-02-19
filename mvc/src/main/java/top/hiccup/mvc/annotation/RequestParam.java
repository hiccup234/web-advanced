package top.hiccup.mvc.annotation;

import java.lang.annotation.*;

/**
 * 方法参数注解
 *
 * @author wenhy
 * @date 2018/8/22
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParam {

    String value() default "";

    String name() default "";

    boolean required() default true;
}
