package cn.clboy.demo.aop.spring;

import cn.clboy.demo.aop.spring.config.AopConfig;
import cn.clboy.demo.aop.spring.service.RoleService;
import cn.clboy.demo.aop.spring.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


/**
 * 使用Execution表达式
 */
public class AnnoExecutionApp {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AopConfig.class);

        RoleService roleService = context.getBean(RoleService.class);
        roleService.add();
        roleService.del();

        UserService userService = context.getBean(UserService.class);
        userService.query();
        userService.del();
    }
}