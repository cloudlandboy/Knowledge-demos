package cn.clboy.demo.shiro.web.p05.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
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
