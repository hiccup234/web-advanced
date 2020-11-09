package top.hiccup.controller;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PreDestroy;

/**
 * F
 *
 * @author wenhy
 * @date 2019/6/25
 */
@RestController
public class MyController2 implements ApplicationListener<ContextClosedEvent>, DisposableBean {

    @RequestMapping("/hello2")
    public String hello(){
        return "hello world!";
    }


    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("ContextClosedEvent222");
    }

    @PreDestroy
    public void shutdown() {
        System.out.println("shutdown222");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("destroy222");
    }
}
