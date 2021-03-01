package top.hiccup;

import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.hiccup.state.OrderServiceImpl;

/**
 * F
 *
 * @author wenhy
 * @date 2021/2/22
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Test.class})
@SpringBootApplication(scanBasePackageClasses = Test.class)
public class Test {

    @Autowired
    private OrderServiceImpl orderService;

    @org.junit.Test
    public void testMultThread() {
        orderService.creat();
        orderService.creat();

        orderService.pay(1);

        new Thread(() -> {
            orderService.deliver(1);
            orderService.receive(1);
        }).start();

        orderService.pay(2);
        orderService.deliver(2);
        orderService.receive(2);

        System.out.println(orderService.getOrders());
    }
}
