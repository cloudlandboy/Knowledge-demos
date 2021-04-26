package cn.clboy.demo.tk.mybatis.springboot.mapper;


import cn.clboy.demo.tk.mybatis.springboot.pojo.Employee;
import tk.mybatis.mapper.common.Mapper;

public interface EmployeeMapper extends Mapper<Employee> {

    Employee selectDeepByPrimaryKey(Integer id);
}