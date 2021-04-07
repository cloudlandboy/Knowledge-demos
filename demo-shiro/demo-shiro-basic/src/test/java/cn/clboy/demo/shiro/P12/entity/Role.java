package cn.clboy.demo.shiro.P12.entity;

import lombok.Data;

import java.io.Serializable;


@Data
public class Role implements Serializable {

    private Long id;

    /**
     * 角色标识 程序中判断使用,如"admin"
     */
    private String role;

    /**
     * 角色描述,UI界面显示使用
     */
    private String description;

    /**
     * 是否可用,如果不可用将不会添加给用户
     */
    private Boolean available = Boolean.FALSE;
}
