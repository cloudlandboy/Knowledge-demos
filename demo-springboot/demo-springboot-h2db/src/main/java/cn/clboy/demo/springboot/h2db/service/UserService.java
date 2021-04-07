package cn.clboy.demo.springboot.h2db.service;

import cn.clboy.demo.springboot.h2db.dao.UserDao;
import cn.clboy.demo.springboot.h2db.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author clboy
 * @Date 2021/3/22 下午3:15
 * @Since 1.0.0
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public List<User> userList(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<User> users = userDao.findAll(pageRequest);
        return users.toList();
    }
}