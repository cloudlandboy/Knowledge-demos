package cn.clboy.demo.mybatis.springboot.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * department
 *
 * @author
 */
@Data
public class Department implements Serializable {
    private Integer id;

    private String departmentname;

    /**
     * 部门下的员工
     */
    public Set<Employee> employees;

    private static final long serialVersionUID = 1L;
}