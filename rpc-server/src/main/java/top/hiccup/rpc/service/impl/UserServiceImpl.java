package top.hiccup.rpc.service.impl;

import top.hiccup.rpc.bean.User;
import top.hiccup.rpc.service.IUserService;

/**
 * 用户信息服务类
 *
 * @author wenhy
 * @date 2019/2/20
 */
public class UserServiceImpl implements IUserService {

    @Override
    public User getUserById(Long id) {
        User user = new User();
        user.setId(234001L);
        user.setName("小海海");
        return user;
    }
}
