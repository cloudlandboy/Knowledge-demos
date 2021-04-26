package cn.clboy.demo.redis.basic.single.jedis.pool;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

/**
 * 测试连接池
 */
public class JedisPoolTest {

    @Test
    public void Test() throws Exception {
        JedisPoolConfig config = new JedisPoolConfig();
        //最大闲置个数
        config.setMaxIdle(5);
        //最大连接数
        config.setMaxTotal(10);
        //连接等待时间
        config.setMaxWaitMillis(5000);

        JedisPool jedisPool = new JedisPool(config, "127.0.0.1", 6379);

        Jedis jedis = jedisPool.getResource();

        jedis.flushDB();

        String status = jedis.set("key1", "value1");
        System.out.println(status);

        Set<String> keys = jedis.keys("*");
        System.out.println(keys);

        jedis.close();
    }
}