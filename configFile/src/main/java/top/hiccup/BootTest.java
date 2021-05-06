package top.hiccup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import top.hiccup.json.Custom;

/**
 * 集成Spring Boot
 *
 * @author wenhy
 * @date 2020/3/20
 */
@SpringBootApplication(scanBasePackageClasses = BootTest.class)
public class BootTest {

    public static void main(String[] args) {

        ConfigurableApplicationContext ctx = SpringApplication.run(BootTest.class, args);
        Custom custom = ctx.getBean(Custom.class);
        System.out.println("\n\n");
        System.out.println(custom.name);
        System.out.println(custom.age);

    }
}
