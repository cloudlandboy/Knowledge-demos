package cn.clboy.demo.shiro.spring.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class LoginInfoDTO implements Serializable {
    private String username;
    private String avatar = "https://ui-avatars.com/api/?name=";

    public LoginInfoDTO(String username) {
        this.username = username;
    }
}