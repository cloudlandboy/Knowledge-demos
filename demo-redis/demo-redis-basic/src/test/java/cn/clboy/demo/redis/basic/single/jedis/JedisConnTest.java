package cn.clboy.demo.redis.basic.single.jedis;


import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class JedisConnTest {

    Jedis jedis;

    /**
     * 获得连接对象
     */
    @Before
    public void before() throws Exception {
        jedis = new Jedis("127.0.0.1", 6379);
    }

    /**
     * 测试连接
     */
    @Test
    public void pingTest() throws Exception {
        String res = jedis.ping();
        System.out.println(res);
    }

    /**
     * 设置密码连接，在 redis.conf 中用 requirepass 设置了密码的情况下
     */
    @Test
    public void authTest() throws Exception {
        String res = jedis.auth("some_pwd");
        System.out.println(res);

        jedis.connect(); //连接
        jedis.disconnect(); //断开连接
    }
}