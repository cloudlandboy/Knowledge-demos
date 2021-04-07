package cn.clboy.demo.ssh.service;

import cn.clboy.demo.ssh.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.UUID;

@ContextConfiguration("classpath:DispatcherServlet.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {


    @Autowired
    UserService userService;

    public void initData(int num) {
        for (int i = 0; i < num; i++) {
            User user = new User();
            user.setUsername("guest-" + i);
            user.setPassword(UUID.randomUUID().toString().substring(0, 12));
            userService.addUser(user);
        }
    }

    @Test
    public void addUser() {
        User user = new User();
        user.setUsername("tom");
        user.setPassword("123456");
        Integer id = userService.addUser(user);
        System.out.println(id);
    }

    @Test
    public void getAllUser() {
        initData(5);
        List<User> allUser = userService.getAllUser();
        System.out.println(allUser);
    }
}