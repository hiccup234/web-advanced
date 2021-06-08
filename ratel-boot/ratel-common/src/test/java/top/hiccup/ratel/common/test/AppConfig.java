package top.hiccup.ratel.common.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 应用配置
 *
 * @author wenhy
 * @date 2021/6/7
 */
@ImportAutoConfiguration(CommonConfig.class)
@SpringBootApplication(scanBasePackageClasses = AppConfig.class)
public class AppConfig {

    public static void main(String[] args) {
        SpringApplication.run(AppConfig.class, args);
    }
}
