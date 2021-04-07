package cn.clboy.demo.aop.spring;

import cn.clboy.demo.aop.spring.config.AopConfig;
import cn.clboy.demo.aop.spring.controller.UserController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashMap;


/**
 * 使用 注解标注
 */
public class AnnoAnnotationApp {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AopConfig.class);
        UserController userController = context.getBean(UserController.class);

        HashMap<String, String> param = new HashMap<>(1);
        param.put("uid", "666");

        userController.userAvatar(param);
        userController.userInfo(param);
    }
}