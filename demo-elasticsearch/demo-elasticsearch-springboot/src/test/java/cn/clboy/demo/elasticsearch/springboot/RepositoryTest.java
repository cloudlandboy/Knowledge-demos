package cn.clboy.demo.elasticsearch.springboot;


import cn.clboy.demo.elasticsearch.springboot.pojo.Person;
import cn.clboy.demo.elasticsearch.springboot.repository.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RepositoryTest {

    @Autowired
    PersonRepository repository;

    /**
     * 添加文档
     */
    @Test
    public void addDocTest() throws Exception {
        Person person = new Person(null, "张三", "他的头发有两寸来长，乱蓬蓬的，活像一个喜鹊窝", 31);
    }


    @Test
    public void customerMethodTest() throws Exception {
        List<Person> persons = repository.findByInfo("油性头发");
        System.out.println(persons);
    }


    /**
     * 高级查询同ElasticsearchRestTemplate， 建议使用 ElasticsearchRestTemplate 操作
     */
}