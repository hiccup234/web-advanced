package top.hiccup.rpc.service;

import top.hiccup.rpc.bean.User;

/**
 * 用户信息服务类
 *
 * @author wenhy
 * @date 2019/2/20
 */
public interface IUserService {

    User getUserById(Long id);
}
