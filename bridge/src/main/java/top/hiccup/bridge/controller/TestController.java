package top.hiccup.bridge.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * SpringMVC测试
 *
 * @author wenhy
 * @date 2018/9/12
 */
@Controller
@RequestMapping("test")
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "/sayHello")
    @ResponseBody
    protected String sayHello(String param, Long sid, String st) {
        return "Hello";
    }
}
