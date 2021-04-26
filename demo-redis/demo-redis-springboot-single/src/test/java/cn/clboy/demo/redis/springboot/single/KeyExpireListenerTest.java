package cn.clboy.demo.redis.springboot.single;

import cn.clboy.demo.redis.springboot.single.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class KeyExpireListenerTest {

    @Autowired
    RedisUtil<String, Object> redisUtil;

    @Test
    public void Test() throws Exception {
        redisUtil.setForTimeMS("key9999", "value9999", 1000 * 30);

        //防止测试时主线程退出
        Thread.sleep(60 * 1000);
    }
}