package com.hiccup.spring.ext.test;

import com.hiccup.mvc.annotation.RequestMapping;
import com.hiccup.mvc.annotation.Controller;
import com.hiccup.mvc.annotation.RequestParam;

/**
 * 测试Controller类
 *
 * @author wenhy
 * @date 2018/8/22
 */
@RequestMapping("test")
@Controller
public class TestController {

    @RequestMapping(value = "query")
    public String query(@RequestParam String req) {
        return "hahaha";
    }

}
