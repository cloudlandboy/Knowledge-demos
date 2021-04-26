package cn.clboy.demo.tk.mybatis.springboot;


import cn.clboy.demo.tk.mybatis.springboot.mapper.DepartmentMapper;
import cn.clboy.demo.tk.mybatis.springboot.pojo.Department;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DepartmentMapperTest {

    @Autowired
    DepartmentMapper departmentMapper;

    @Test
    public void selectTest() throws Exception {
        Department department = departmentMapper.selectByPrimaryKey(1);
        System.out.println(department);
    }

    @Test
    public void selectByExampleTest() throws Exception {
        Example example = new Example(Department.class);
        example.createCriteria().andLike("departmentname", "人力%");
        List<Department> departments = departmentMapper.selectByExample(example);
        System.out.println(departments);
    }

    @Test
    public void selectDeepTest() throws Exception {
        Department department = departmentMapper.selectDeepByPrimaryKey(2);
        System.out.println("部门:" + department.getDepartmentname());
        System.out.println("员工列表:");
        department.getEmployees().forEach(System.out::println);
    }
}