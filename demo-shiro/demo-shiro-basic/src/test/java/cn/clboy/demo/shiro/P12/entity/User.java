package cn.clboy.demo.shiro.P12.entity;

import lombok.Data;

import java.io.Serializable;


@Data
public class User implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String salt;
    private Boolean locked = Boolean.FALSE;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
