package cn.clboy.demo.springcloud.zuul.service.user.controller;


import cn.clboy.demo.springcloud.zuul.service.user.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/{id}")
    public User findUser(@PathVariable String id) {
        User user = new User();
        user.setId(id);
        user.setName("NO-" + id);
        user.setCreateTime(new Date());
        return user;
    }
}