package cn.clboy.demo.tk.mybatis.springboot.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;


@Data
public class Employee implements Serializable {

    @Id
    @GeneratedValue(generator = "JDBC")
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