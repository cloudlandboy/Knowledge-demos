package cn.clboy.demo.mybatis.springboot;


import cn.clboy.demo.mybatis.springboot.mapper.DepartmentMapper;
import cn.clboy.demo.mybatis.springboot.pojo.Department;
import cn.clboy.demo.mybatis.springboot.pojo.DepartmentExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DepartmentMapperTest {

    @Autowired
    DepartmentMapper departmentMapper;


    @Test
    public void insertTest() throws Exception {
        Department department = new Department();
        department.setDepartmentname("开发部");
        int insert = departmentMapper.insert(department);
        System.out.printf("受影响的行数:%s%n", insert);
        System.out.println(department);
    }

    @Test
    public void updateTest() throws Exception {
        Department department = new Department();
        department.setId(1);
        department.setDepartmentname("修改部");
        int insert = departmentMapper.updateByPrimaryKey(department);
        System.out.printf("受影响的行数:%s%n", insert);
        System.out.println(department);
    }

    @Test
    public void deleteTest() throws Exception {
        int insert = departmentMapper.deleteByPrimaryKey(1);
        System.out.printf("受影响的行数:%s%n", insert);
    }

    @Test
    public void selectTest() throws Exception {
        Department department = departmentMapper.selectByPrimaryKey(1);
        System.out.println(department);
    }

    @Test
    public void selectByExampleTest() throws Exception {
        DepartmentExample departmentExample = new DepartmentExample();
        departmentExample.createCriteria()
                .andDepartmentnameEqualTo("财务部");
        List<Department> departments = departmentMapper.selectByExample(departmentExample);
        System.out.println(departments);
    }

    @Test
    public void selectDeepTest() throws Exception {
        Department department = departmentMapper.selectDeepByPrimaryKey(1);
        System.out.println("部门:" + department.getDepartmentname());
        System.out.println("员工列表:");
        department.getEmployees().forEach(System.out::println);
    }

    /**
     * 测试一级缓存
     * <p>
     * 控制台查看发送sql
     */
    @Test
    @Transactional
    public void sessionCacheTest() throws Exception {
        //DEBUG
        Department department = departmentMapper.selectByPrimaryKey(1);
        System.out.println(department);
        Department reFind = departmentMapper.selectByPrimaryKey(1);
        System.out.println(reFind);
    }
}