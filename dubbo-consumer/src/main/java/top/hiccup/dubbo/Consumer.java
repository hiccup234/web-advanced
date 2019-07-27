package top.hiccup.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import top.hiccup.dubbo.service.DemoService;

/**
 * 消费者
 *
 * @author wenhy
 * @date 2019/7/26
 */
public class Consumer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
        context.start();
        System.out.println("consumer start..");
        DemoService demoService = (DemoService) context.getBean("demoService");
        System.out.println(demoService.sayHello("hiccup"));
    }
}