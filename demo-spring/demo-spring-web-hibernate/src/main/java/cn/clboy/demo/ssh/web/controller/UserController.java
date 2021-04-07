package cn.clboy.demo.ssh.web.controller;


import cn.clboy.demo.ssh.entity.User;
import cn.clboy.demo.ssh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;


    /**
     * 查询全部用户
     */
    @GetMapping
    public String users(Model model) {
        List<User> users = userService.getAllUser();
        model.addAttribute("users", users);
        return "/user/list";
    }


    /**
     * 获取用户
     */
    @GetMapping("/{id}")
    public String user(@PathVariable Integer id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute(user);
        return "/user/info";
    }

    /**
     * 添加用户
     */
    @PostMapping
    public String addUser(@Valid User user) {
        userService.addUser(user);
        return "redirect:/user";
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(null);
    }

    /**
     * 修改用户
     */
    @PutMapping("/{id}")
    public String updateUser(@Valid User user) {
        userService.updateUser(user);
        return "redirect:/user/" + user.getId();
    }


    /**
     * 跳转添加用户的页面
     */
    @GetMapping("/add")
    public String toAdd() {
        return "/user/add";
    }

    /**
     * 跳转编辑用户的页面
     */
    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") Integer id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute(user);
        return "/user/edit";
    }
}