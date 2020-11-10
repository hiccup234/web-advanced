package top.hiccup.controller;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
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
public class MyController3 implements ApplicationListener<ContextRefreshedEvent> {

    @RequestMapping("/hello3")
    public String hello() {
        return "hello world!";
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("ContextRefreshedEvent");
    }

    @PreDestroy
    public void shutdown() {
        System.out.println("shutdown333");
    }

}
