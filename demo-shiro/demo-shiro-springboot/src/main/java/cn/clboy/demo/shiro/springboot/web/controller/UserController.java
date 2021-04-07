package cn.clboy.demo.shiro.springboot.web.controller;


import cn.clboy.demo.shiro.springboot.entity.User;
import cn.clboy.demo.shiro.springboot.service.UserService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author clboy
 * @Date 2021/2/2 下午3:02
 * @Since 1.0.0
 */

@Controller
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/users")
    public String userList(Model model) {
        List<User> users = userService.getUsers();
        String data = JSON.toJSONString(users);
        model.addAttribute("users", data);
        return "admin/user/userList";
    }

    @PostMapping("/user")
    public String addUser(User user) {
        userService.saveUser(user);
        return "redirect:/admin/user/users";
    }
}