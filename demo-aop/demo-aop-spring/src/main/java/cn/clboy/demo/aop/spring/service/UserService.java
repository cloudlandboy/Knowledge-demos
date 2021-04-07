package cn.clboy.demo.aop.spring.service;


import org.springframework.stereotype.Service;

@Service
public class UserService {

    public void add() {
        System.out.println("添加用户");
    }

    public void query() {
        System.out.println("查询用户");
    }

    public void update() {
        System.out.println("更新用户");
    }

    public void del() {
        throw new RuntimeException("不允许删除用户！");
    }
}