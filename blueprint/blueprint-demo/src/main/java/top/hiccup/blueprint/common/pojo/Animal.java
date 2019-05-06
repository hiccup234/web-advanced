package top.hiccup.blueprint.common.pojo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;

/**
 * Created by wenhy on 2018/1/24.
 */
public class Animal implements BeanNameAware, BeanFactoryAware, InitializingBean, DisposableBean{

    private String name;
    private Integer age;

    public Animal() {
        System.out.println("Step1：调用无参构造器");
    }

    public Animal(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        System.out.println("Step2：设置成员变量name");
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
        System.out.println("Step2：设置成员变量age");
    }


    @Override
    public void setBeanName(String s) {
        System.out.println("Step3：设置bean的ID属性为："+s);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("Step4：获取beanFactory容器为："+beanFactory);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Step6：执行<afterPropertiesSet>后：Bean已经初始化完毕");
    }

    // 生命周期方法：init-method
    public void init() {
        System.out.println("Step7：初始化完毕之后：执行用户定制的<init>方法");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("Step9：销毁之前：执行框架<destroy>方法");
    }

    // 生命周期方法：destroy-method
    //      1）当前Bean的作用域要是singleton
    //      2）要手工关闭容器
    public void close() {
        System.out.println("Step10：销毁之前：执行用户定制的<close>方法");
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

}
