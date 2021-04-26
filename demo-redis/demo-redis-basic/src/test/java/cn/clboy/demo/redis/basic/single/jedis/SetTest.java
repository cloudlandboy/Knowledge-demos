package cn.clboy.demo.redis.basic.single.jedis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Set;

public class SetTest {

    Jedis jedis;

    @Before
    public void before() throws Exception {
        jedis = new Jedis("127.0.0.1", 6379);
        //清空数据库
        jedis.flushDB();

        //向set中添加数据
        jedis.sadd("set1", "v1", "v2", "v3", "v4", "v5");

    }

    @Test
    public void Test() throws Exception {
        Set<String> values = jedis.smembers("set1");
        System.out.format("smembers：获取 set 中所有的成员：%s%n%n", values);

        Long size = jedis.scard("set1");
        System.out.format("scard：获取 set 中成员数量：%s%n%n", size);

        Boolean existV6 = jedis.sismember("set1", "v6");
        Boolean existV5 = jedis.sismember("set1", "v5");
        System.out.format("sismember：判断 set 中是否存在某个成员：%n");
        System.out.format("\tv6：%s%n", existV6);
        System.out.format("\tv5：%s%n%n", existV5);

        Long remCount = jedis.srem("set1", "v1", "v5");
        System.out.format("srem：删除 set 中是否存在某些成员：%s%n%n", remCount);

        System.out.println(jedis.smembers("set1"));

    }

    @Test
    public void setOperationTest() throws Exception {
        //准备两个集合
        jedis.sadd("set2", "v1", "v3", "v5", "v7", "v9");
        jedis.sadd("set3", "v1", "v3", "v6", "v7", "v10");
        jedis.sadd("set4", "v1", "v2", "v4", "v8", "v10");

        //差集 取set2与其他几个集合差集，也就是set2有的，其他集合没有的
        Set<String> diff = jedis.sdiff("set2", "set3", "set4");
        System.out.format("sdiff：取set2与其他几个集合差集：%s%n%n", diff);

        //交集
        Set<String> inter = jedis.sinter("set2", "set3", "set4");
        System.out.format("sinter：取几个集合交集：%s%n%n", inter);

        //并集，联合查询
        Set<String> union = jedis.sunion("set2", "set3", "set4");
        System.out.format("sunion：取几个集合并集：%s%n%n", union);

    }
}
