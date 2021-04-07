package cn.clboy.demo.ssh.service;


import cn.clboy.demo.ssh.dao.UserDao;
import cn.clboy.demo.ssh.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Transactional(readOnly = false)
    public Integer addUser(User user) {
        return userDao.addUser(user);
    }

    public List<User> getAllUser() {
        return userDao.getAllUser();
    }

    @Transactional(readOnly = false)
    public void deleteUser(Integer id) {
        userDao.deleteUser(id);
    }

    @Transactional(readOnly = false)
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    public User getUser(Integer id) {
        return userDao.getUser(id);
    }
}