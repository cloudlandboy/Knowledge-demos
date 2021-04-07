package cn.clboy.demo.shiro.springboot.web.controller;

import cn.clboy.demo.shiro.springboot.entity.Permission;
import cn.clboy.demo.shiro.springboot.service.PermissionService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author clboy
 * @Date 2021/2/4 下午4:31
 * @Since 1.0.0
 */
@Controller
@RequestMapping("/admin")
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    @GetMapping("/permission/permissions")
    public String roleList(Model model) {
        List<Permission> permissions = permissionService.getPermissions();

        String data = JSON.toJSONString(permissions);
        model.addAttribute("permissions", data);
        return "admin/permission/permissionList";
    }
}