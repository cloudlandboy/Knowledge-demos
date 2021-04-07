package cn.clboy.demo.springboot.h2db.controller;

import cn.clboy.demo.springboot.h2db.entity.User;
import cn.clboy.demo.springboot.h2db.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author clboy
 * @Date 2021/3/22 下午3:14
 * @Since 1.0.0
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<User> userList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size) {
        return userService.userList(page, size);
    }
}