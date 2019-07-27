package top.hiccup.dubbo.service.impl;

import top.hiccup.dubbo.service.DemoService;

/**
 * Demo实现类
 *
 * @author wenhy
 * @date 2019/7/26
 */
public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {
        return "你好：" + name;
    }
}
