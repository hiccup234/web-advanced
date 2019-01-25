package top.hiccup.ratel.im.service;

import top.hiccup.ratel.im.bean.User;

/**
 * 用户信息服务类
 *
 * @author wenhy
 * @date 2019/1/25
 */
public interface UserService {

    /**
     * 通过ID查询用户
     * @param userId
     * @return
     */
    User queryUserById(long userId);

    /**
     * 通过账号查询用户
     * @param acct
     * @return
     */
    User queryUserByAcct(String acct);
}
