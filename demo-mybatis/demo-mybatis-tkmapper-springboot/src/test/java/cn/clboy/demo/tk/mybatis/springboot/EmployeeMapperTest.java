package cn.clboy.demo.tk.mybatis.springboot;


import cn.clboy.demo.tk.mybatis.springboot.mapper.EmployeeMapper;
import cn.clboy.demo.tk.mybatis.springboot.pojo.Employee;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
public class EmployeeMapperTest {

    @Autowired
    EmployeeMapper employeeMapper;


    @Test
    public void selectDeepTest() throws Exception {
        Employee employee = employeeMapper.selectDeepByPrimaryKey(1);
        System.out.println(employee);
    }

    /**
     * 测试pageHelper插件，需要引入项目中有mybatis-spring-boot-starter的依赖
     * <p>
     * 只使用tk.mapper的依赖pageHelper会失效
     */
    @Test
    public void Test() throws Exception {
        Page<Object> page = PageHelper.startPage(-2, 5, true);
        List<Employee> employees = employeeMapper.selectByExample(null);

        System.out.format("总共 %d条 数据，共%d页。%n", page.getTotal(), page.getPages());
        employees.forEach(System.out::println);
    }

}