package com.hiccup.blueprint.common;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wenhy on 2018/1/27.
 */
//@Configuration  // 配置为Spring容器，记得要在xml中加<context:component-scan base-package="">
//public class MySpringConfig {
//
//    @Bean(name="stu", autowire= Autowire.BY_TYPE)
//    public Student myStudentCreator() {
//        return new Student();
//    }
//
//    // byName方式限制域属性名必须要跟bean的Id相同
//    @Bean(name="stu2", autowire= Autowire.BY_NAME)
//    public Student myStudentCreator2() {
//        return new Student();
//    }
//
//    @Bean("doSomeService")
//    public IDoSomeService myDoSomeServiceCreator() {
//        return new DoSomeServiceImpl();
//    }
//}
