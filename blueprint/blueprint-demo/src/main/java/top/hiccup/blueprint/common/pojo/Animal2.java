package top.hiccup.blueprint.common.pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by wenhy on 2018/1/26.
 */

//@Repository   DAO
//@Service  SMO
//@Controller   MVC
@Component(value="stu")
@Scope(value="prototype")
public class Animal2 {

    @Value("张三")
    private String name;
    @Value("21")
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Animal2{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Autowired  //byType注解方式
    private Animal animal;


    @Autowired  //byName注解方式：要求Autowired与Qualifier联合使用
    @Qualifier(value="doSomeService")
    private Animal animal2;

    //    @javax.annotation.Resource      // 域属性(引用)注解：byType方式
    @javax.annotation.Resource(name="doSomeService")    // 域属性(引用)注解：byName方式
    private Animal animal3;

    @PostConstruct  // init-method方法注解
    public void init() {

    }

    @PreDestroy   // destroy-method方法注解
    public void close() {

    }

}
