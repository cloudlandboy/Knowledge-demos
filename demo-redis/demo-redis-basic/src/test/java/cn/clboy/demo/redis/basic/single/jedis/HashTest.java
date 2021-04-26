package cn.clboy.demo.redis.basic.single.jedis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HashTest {

    Jedis jedis;

    @Before
    public void before() throws Exception {
        jedis = new Jedis("127.0.0.1", 6379);
        //清空数据库
        jedis.flushDB();

        //添加单个数据
        Long affect = jedis.hset("hash1", "key1", "value1");
        System.out.println(affect);

        //添加多个数据
        Map<String, String> data = new HashMap<String, String>();
        data.put("key2", "value2");
        data.put("key3", "value3");
        data.put("key4", "value4");
        data.put("key5", "value5");

        String status = jedis.hmset("hash1", data);
        System.out.println(status);
    }


    @Test
    public void test() throws Exception {

        Map<String, String> hash = jedis.hgetAll("hash1");
        System.out.format("hgetAll：散列 hash 的所有键值对(key:value)为：%s%n%n", hash);

        Set<String> keys = jedis.hkeys("hash1");
        System.out.format("hkeys：散列 hash 的所有键 (key) 为：%s%n%n", keys);

        List<String> values = jedis.hvals("hash1");
        System.out.format("hvals：散列 hash 的所有值 (value) 为：%s%n%n", values);

        Long incrementedVal = jedis.hincrBy("hash1", "key6", 6);
        System.out.format("hincrBy：将散列 hash 的 某个key 自增 x 后的值为：%s%n%n", incrementedVal); //不存在则添加


        Long affect = jedis.hdel("hash1", "key1", "key2");
        System.out.format("hdel：删除 hash 中的 n 个 key：%s%n%n", affect);

        Long length = jedis.hlen("hash1");
        System.out.format("hlen：散列 hash 中键值对的个数：%s%n%n", length);

        Boolean hexists = jedis.hexists("hash1", "key6");
        System.out.format("hexists：判断 hash 中是否存在  某个key：%s%n%n", hexists);

        String key6_val = jedis.hget("hash1", "key6");
        System.out.format("hget：获取 hash 中 某个key的value：%s%n%n", key6_val);

        List<String> some_val = jedis.hmget("hash1", "key1", "key3", "key5");
        System.out.format("hmget：获取 hash 中 多个key的value：%s%n%n", some_val);
    }
}
