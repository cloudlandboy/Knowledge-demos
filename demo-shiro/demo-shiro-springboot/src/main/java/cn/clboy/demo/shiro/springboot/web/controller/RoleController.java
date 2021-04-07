package cn.clboy.demo.shiro.springboot.web.controller;


import cn.clboy.demo.shiro.springboot.dto.RoleInfoDTO;
import cn.clboy.demo.shiro.springboot.entity.Role;
import cn.clboy.demo.shiro.springboot.service.RoleService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author clboy
 * @Date 2021/2/4 下午4:31
 * @Since 1.0.0
 */
@Controller
@RequestMapping("/admin")
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping("/role/roles")
    public String roleList(Model model) {
        List<Role> roles = roleService.getRoles();
        String data = JSON.toJSONString(roles);
        model.addAttribute("roles", data);
        return "admin/role/roleList";
    }

    /**
     * 查看角色详情
     */
    @GetMapping("/role/{id}")
    public String roleInfo(@PathVariable("id") String id, Model model) {
        RoleInfoDTO roleInfo = roleService.getRoleAndPermissions(id);
        model.addAttribute("roleInfo", roleInfo);
        return "admin/role/roleInfo";
    }
}