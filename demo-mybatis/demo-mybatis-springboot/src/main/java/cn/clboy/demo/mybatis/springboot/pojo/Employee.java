package cn.clboy.demo.mybatis.springboot.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * employee
 *
 * @author
 */
@Data
public class Employee implements Serializable {
    private Integer id;

    private String lastname;

    private String email;

    private Integer gender;

    private Integer dId;

    /**
     * 员工所属部门
     */
    private Department department;

    private static final long serialVersionUID = 1L;
}