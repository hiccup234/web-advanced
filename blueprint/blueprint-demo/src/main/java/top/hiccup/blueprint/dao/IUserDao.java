package top.hiccup.blueprint.dao;

import top.hiccup.blueprint.entity.po.User;

/**
 * 用户信息Dao接口类
 *
 * @author wenhy
 * @date 2018/7/23
 */
public interface IUserDao {

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
