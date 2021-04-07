package cn.clboy.demo.shiro.spring.service.impl;

import cn.clboy.demo.shiro.spring.entity.User;
import cn.clboy.demo.shiro.spring.mapper.UserMapper;
import cn.clboy.demo.shiro.spring.service.UserService;
import cn.clboy.demo.shiro.spring.utils.PasswordHelper;

import java.util.List;
import java.util.Set;


public class UserServiceImpl implements UserService {

    private UserMapper userMapper;
    private PasswordHelper passwordHelper;

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    public void setPasswordHelper(PasswordHelper passwordHelper) {
        this.passwordHelper = passwordHelper;
    }

    /**
     * 创建用户
     *
     * @param user
     */
    public void createUser(User user) {
        //加密密码
        passwordHelper.encryptPassword(user);
        userMapper.createUser(user);
    }

    /**
     * 修改密码
     *
     * @param userId
     * @param newPassword
     */
    public void changePassword(Long userId, String newPassword) {
        User user = userMapper.findOne(userId);
        user.setPassword(newPassword);
        passwordHelper.encryptPassword(user);
        userMapper.updateUser(user);
    }

    /**
     * 添加用户-角色关系
     *
     * @param userId
     * @param roleIds
     */
    public void correlationRoles(Long userId, Long... roleIds) {
        userMapper.correlationRoles(userId, roleIds);
    }


    /**
     * 移除用户-角色关系
     *
     * @param userId
     * @param roleIds
     */
    public void uncorrelationRoles(Long userId, Long... roleIds) {
        userMapper.uncorrelationRoles(userId, roleIds);
    }

    /**
     * 根据用户名查找用户
     *
     * @param username
     * @return
     */
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    /**
     * 根据用户名查找其角色
     *
     * @param username
     * @return
     */
    public Set<String> findRoles(String username) {
        return userMapper.findRoles(username);
    }

    /**
     * 根据用户名查找其权限
     *
     * @param username
     * @return
     */
    public Set<String> findPermissions(String username) {
        return userMapper.findPermissions(username);
    }

    @Override
    public List<User> getUsers() {
        return userMapper.findUsers();
    }

    @Override
    public void saveUser(User user) {
        passwordHelper.encryptPassword(user);
        userMapper.createUser(user);
    }

    @Override
    public boolean existsUserByName(String username) {
        return userMapper.existsUserByName(username) > 0;
    }
}
