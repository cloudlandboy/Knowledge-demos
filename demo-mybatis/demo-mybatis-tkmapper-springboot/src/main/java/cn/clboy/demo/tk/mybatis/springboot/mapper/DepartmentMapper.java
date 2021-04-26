package cn.clboy.demo.tk.mybatis.springboot.mapper;


import cn.clboy.demo.tk.mybatis.springboot.pojo.Department;
import tk.mybatis.mapper.common.Mapper;

public interface DepartmentMapper extends Mapper<Department> {

    Department selectDeepByPrimaryKey(Integer id);
}