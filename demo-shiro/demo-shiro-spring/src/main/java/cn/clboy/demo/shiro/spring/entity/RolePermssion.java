package cn.clboy.demo.shiro.spring.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 角色和权限关系
 */
@Getter
@Setter
public class RolePermssion implements Serializable {

    private Long roleId;

    private Long permissionId;
}
