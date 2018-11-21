package top.hiccup.controller;

import top.hiccup.mvc.annotation.RequestMapping;
import top.hiccup.mvc.annotation.Controller;
import top.hiccup.mvc.annotation.RequestParam;

/**
 * 测试Controller类
 *
 * @author wenhy
 * @date 2018/8/22
 */
@RequestMapping("/test")
@Controller
public class TestController {

    @RequestMapping(value = "/query")
    public String query(@RequestParam String req) {
        String str = "Hello World";
        str.hashCode();
        return "hahaha";
    }

}
