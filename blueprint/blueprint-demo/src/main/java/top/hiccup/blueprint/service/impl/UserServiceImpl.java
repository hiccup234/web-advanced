package top.hiccup.blueprint.service.impl;

import top.hiccup.blueprint.dao.IUserDao;
import top.hiccup.blueprint.entity.po.User;
import top.hiccup.blueprint.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户信息Service实现类
 *
 * @author wenhy
 * @date 2018/2/4
 */
@Service("userService")
public class UserServiceImpl implements IUserService{

    @Autowired
    @Qualifier("IUserDao")
    private IUserDao userDao;

    /**
     * 通过注解替方法加上事务，申明式事务不会更好吗？
     * @param user
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer addUser(User user) {
        return userDao.addUser(user);
    }

    @Override
    public User queryUserById(Long userId) {
        return userDao.queryUserById(userId);
    }

}
