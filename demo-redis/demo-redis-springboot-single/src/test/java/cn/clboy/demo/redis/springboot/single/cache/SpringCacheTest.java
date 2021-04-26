package cn.clboy.demo.redis.springboot.single.cache;


import cn.clboy.demo.redis.springboot.single.pojo.Person;
import cn.clboy.demo.redis.springboot.single.service.PersonService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringCacheTest {

    @Autowired
    PersonService personService;


    /**
     * 添加之后，使用redis-cli可以看到添加了缓存
     */
    @Test
    public void saveOrUpdateTest() throws Exception {
        Person person = new Person("隐冬卉", "一丛稀疏而干枯的头发，像小鸭的绒毛点缀在头顶上", 31);
        personService.saveOrUpdate(person);
        System.out.println(person);
    }

    /**
     * 去先去缓存中找，找不到才会去数据库中查询
     * <p>
     * 第二次运行就不会打印：到数据库查询
     */
    @Test
    public void getTest() throws Exception {
        Person person = personService.getPerson("1");
        System.out.println(person);
    }

    /**
     * 删除之后再运行上面的查询测试
     */
    @Test
    public void delTest() throws Exception {
        personService.delete("1");
    }

    /**
     * 测试防止主线程退出
     *
     * 本案例用于测试监听key过期事件
     */
    @After
    public void Test() throws Exception {
        Thread.sleep(60000*30);
    }
}