package cn.clboy.demo.redis.springboot.single;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PublishTest {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Test
    public void publishTest() throws Exception {
        redisTemplate.convertAndSend("test-queue", "hello,springboot");
        System.out.println("消息已发送");
        //防止测试时主线程退出
        Thread.sleep(10000);
    }
}