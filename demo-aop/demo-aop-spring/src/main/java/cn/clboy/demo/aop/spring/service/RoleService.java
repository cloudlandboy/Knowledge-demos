package cn.clboy.demo.aop.spring.service;


import org.springframework.stereotype.Service;

@Service
public class RoleService {

    public void add(){
        System.out.println("添加角色");
    }

    public void del(){
        System.out.println("删除角色");
    }
}