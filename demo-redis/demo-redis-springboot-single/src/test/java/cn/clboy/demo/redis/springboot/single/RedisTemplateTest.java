package cn.clboy.demo.redis.springboot.single;


import cn.clboy.demo.redis.springboot.single.pojo.Person;
import cn.clboy.demo.redis.springboot.single.utils.RedisUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * redis-springboot提供了redisTemplate和stringRedisTemplate两个模板工具类
 * <p>
 * stringRedisTemplate用于操作value为字符串
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTemplateTest {


    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisUtil<String,Object> redisUtil;

    @Before
    public void before() throws Exception {
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        connection.flushDb();
    }

    /**
     * 插入之后用redis-cli客户端工具查看key和value都是乱码，但是程序却能正常读取
     * <p>
     * 原因是redisTemplate默认使用的序列化工具是 jdk序列化
     *
     * @see RedisTemplate#afterPropertiesSet()
     * <p>
     * 而stringRedisTemplate是将key和value都转为string
     * @see StringRedisTemplate#StringRedisTemplate()
     */
    @Test
    public void redisTemplateTest() throws Exception {
        redisTemplate.opsForValue().set("key1", "hello redisTemplate");
        Object value = redisTemplate.opsForValue().get("key1");
        System.out.println(value);
    }


    @Test
    public void stringRedisTemplateTest() throws Exception {
        stringRedisTemplate.opsForValue().set("key2", "value2");
        String value = stringRedisTemplate.opsForValue().get("key2");
        System.out.println(value);
    }


    @Test
    public void jsonRedisTemplateTest() throws Exception {
        Person person = new Person("13","小明", "爱学数学的家伙。", 29);
        redisUtil.set("xiaoMing",person);

        Object xiaoHua = redisUtil.get("xiaoMing");
        System.out.println(xiaoHua);

    }
}