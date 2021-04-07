package cn.clboy.demo.shiro.spring.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 用户角色关系
 */
@Getter
@Setter
public class UserRole implements Serializable {
    private Long userId;
    private Long roleId;
}
