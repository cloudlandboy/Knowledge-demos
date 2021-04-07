package cn.clboy.demo.springcloud.feign.consumer.controller;

import cn.clboy.demo.springcloud.feign.consumer.client.UserClient;
import cn.clboy.demo.springcloud.feign.provider.pojo.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author cloudlandboy
 * @Date 2019/11/26 下午8:29
 * @Since 1.0.0
 */

@RestController
@RequestMapping("/feign/user")
public class UserController {

    @Autowired
    UserClient userClient;

    @GetMapping("/{id}")
    public TbUser getUserById(@PathVariable Long id) {
        TbUser tbUser = userClient.queryUserById(id);
        return tbUser;
    }
}