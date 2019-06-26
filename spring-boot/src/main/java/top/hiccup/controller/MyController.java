package top.hiccup.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * F
 *
 * @author wenhy
 * @date 2019/6/25
 */
@RestController
public class MyController {

    @RequestMapping("/")
    public String hello(){
        return "hello world!";
    }
}
