package cn.clboy.demo.redis.basic.single.jedis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class StringTest {
    Jedis jedis;

    @Before
    public void before() throws Exception {
        jedis = new Jedis("127.0.0.1", 6379);
        //清空数据库
        jedis.flushDB();

        //添加数据
        String status = jedis.set("key1", "value1");
        System.out.format("set 添加key：%s%n%n", status);
    }


    @Test
    public void Test() throws Exception {
        String value = jedis.get("key1");
        System.out.format("get 获取key：%s%n%n", value);

        Long strLength = jedis.append("key1", "==="); //如果该 key 不存在，则重新创建一个key/value
        System.out.format("append 追加value：%s%n", strLength);
        System.out.format("\tkey1：%s%n%n", jedis.get("key1"));

        Long affect = jedis.expire("key1", 5);
        System.out.format("expire 设置key的过期时间(单位秒)：%s%n", affect);
        Thread.sleep(6000);
        System.out.format("\t6秒后获取key1：%s%n%n", jedis.get("key1"));


    }
}
