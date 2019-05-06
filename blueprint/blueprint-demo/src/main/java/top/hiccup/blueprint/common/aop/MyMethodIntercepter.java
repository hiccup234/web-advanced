package top.hiccup.blueprint.common.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Created by wenhy on 2018/1/28.
 */
public class MyMethodIntercepter implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
//        methodInvocation.getMethod().invoke(methodInvocation.getThis(), methodInvocation.getArguments());
        System.out.println("around advice: before");
        Object result = methodInvocation.proceed();
        System.out.println("around advice: after");
        // 这里可以修改切入点方法的返回值
        if(null != result) {
            result = ((String)result).toUpperCase();
        }
        return result;
    }
}
