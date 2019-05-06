package top.hiccup.blueprint.common.aop;

import org.springframework.aop.ThrowsAdvice;

/**
 * Created by wenhy on 2018/1/28.
 */
public class MyThrowsAdvice implements ThrowsAdvice {

    public void afterThrowing(Exception e) {
        System.out.println("抛出了异常：");
        e.printStackTrace();
    }
    public void afterThrowing(MyException e) {
        System.out.println("抛出自定义异常：");
        e.printStackTrace();
    }

}
