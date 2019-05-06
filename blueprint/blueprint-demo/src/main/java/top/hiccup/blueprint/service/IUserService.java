package top.hiccup.blueprint.service;

import top.hiccup.blueprint.entity.po.User;

/**
 * Created by wenhy on 2018/2/4.
 */
public interface IUserService {

    /**
     * 添加用户
     * @param user
     * @return
     */
    Integer addUser(User user);

    /**
     * 通过ID查询用户信息
     * @param userId
     * @return
     */
    User queryUserById(Long userId);
}
