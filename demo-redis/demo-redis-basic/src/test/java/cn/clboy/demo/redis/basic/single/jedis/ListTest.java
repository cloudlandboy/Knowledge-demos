package cn.clboy.demo.redis.basic.single.jedis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;

public class ListTest {

    Jedis jedis;

    @Before
    public void before() throws Exception {
        jedis = new Jedis("127.0.0.1", 6379);
        //清空数据库
        jedis.flushDB();

        //添加一些数据
        jedis.rpush("list1", "v1", "v2", "v3", "v4", "v5");

    }

    @Test
    public void Test() throws Exception {
        List<String> values = jedis.lrange("list1", 0, -1);
        System.out.format("lrange：获取 list 中 start-stop 范围的值(stop可以为负数，为倒数第几个)：%s%n%n", values);

        Long length = jedis.llen("list1");
        System.out.format("hlen：获取 list 的元素个数：%s%n%n", length);

        String left_el = jedis.lpop("list1");
        System.out.format("lpop：从 list 头部弹出一个元素：%s%n%n", left_el);

        String right_el = jedis.rpop("list1");
        System.out.format("rpop：从 list尾部弹出一个元素：%s%n%n", right_el);

        System.out.println(jedis.lrange("list1", 0, -1)); //[v2, v3, v4]
    }

}
