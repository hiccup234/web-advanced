package top.hiccup.blueprint.common.aop;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * Created by wenhy on 2018/1/28.
 */
public class MyAfterAdvice implements AfterReturningAdvice {
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        // returnValue为切入点方法的返回值，但是在advice里无法改变returnValue
        System.out.println("after advice："+returnValue);
        if(null != returnValue) {
            returnValue = ((String) returnValue).toUpperCase();
        }
    }
}
