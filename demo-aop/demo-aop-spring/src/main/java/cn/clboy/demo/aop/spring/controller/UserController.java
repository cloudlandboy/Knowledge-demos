package cn.clboy.demo.aop.spring.controller;


import cn.clboy.demo.aop.spring.annos.RequireToken;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class UserController {

    @RequireToken("uid")
    public void userAvatar(Map<String, String> reqParam) {
        System.out.println("http://www.baidu.com/xxx.png");
    }

    @RequireToken
    public void userInfo(Map<String, String> reqParam) {
        System.out.println("用户信息：......");
    }
}