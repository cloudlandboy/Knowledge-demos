package cn.clboy.demo.shiro.springboot.dto;

import lombok.Data;

import java.util.Set;

/**
 * @Author clboy
 * @Date 2021/2/5 上午11:30
 * @Since 1.0.0
 */
@Data
public class UserInfoDTO extends LoginInfoDTO {
    private Set<String> roles;
    private Boolean locked;
}