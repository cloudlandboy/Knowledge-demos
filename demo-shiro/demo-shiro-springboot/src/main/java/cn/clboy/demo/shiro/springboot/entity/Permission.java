package cn.clboy.demo.shiro.springboot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
public class Permission implements Serializable {

    private Long id;

    /**
     * 权限标识 程序中判断使用,如"user:create"
     */
    private String permission;

    /**
     * 权限描述,UI界面显示使用
     */
    private String description;

    /**
     * 是否可用,如果不可用将不会添加给用户
     */
    private Boolean available = Boolean.FALSE;

    public Permission(String permission, String description, Boolean available) {
        this.permission = permission;
        this.description = description;
        this.available = available;
    }
}
