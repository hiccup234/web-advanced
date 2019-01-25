package top.hiccup.ratel.im.service.impl;

import java.util.ArrayList;
import java.util.List;

import top.hiccup.ratel.im.service.UserService;
import top.hiccup.ratel.im.bean.User;

/**
 * 用户信息服务类
 *
 * @author wenhy
 * @date 2019/1/25
 */
public class UserServiceImpl implements UserService {

    private static List<User> users = new ArrayList<>(20);
    static {
        users.add(new User(1001, "小海海", "hiccup", "234234"));
        users.add(new User(1002, "东东", "dongdong", "234234"));
        users.add(new User(1003, "小海海2", "hiccup", "234234"));
        users.add(new User(1004, "小海海3", "hiccup", "234234"));
    }

    @Override
    public User queryUserById(long userId) {
        for (User u : users) {
            if (u.getUserId() == userId) {
                return u;
            }
        }
        return null;
    }

    @Override
    public User queryUserByAcct(String acct) {
        for (User u : users) {
            if (u.getAccount().equals(acct)) {
                return u;
            }
        }
        return null;
    }
}
