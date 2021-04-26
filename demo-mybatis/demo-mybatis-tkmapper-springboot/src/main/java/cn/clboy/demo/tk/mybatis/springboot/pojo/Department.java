package cn.clboy.demo.tk.mybatis.springboot.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Set;


@Data
public class Department implements Serializable {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String departmentname;

    /**
     * 部门下的员工
     */
    public Set<Employee> employees;

    private static final long serialVersionUID = 1L;
}