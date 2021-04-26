package cn.clboy.demo.redis.basic.single.jedis.tx;


import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * 测试事物
 */
public class TxTest {

    Jedis jedis;

    @Before
    public void before() throws Exception {
        jedis = new Jedis("127.0.0.1", 6379);
        jedis.flushDB();
        jedis.set("user1_account", "550");
        jedis.set("user2_account", "200");
    }

    /**
     * 事物
     * 模拟user1向user2转账50元
     */
    @Test
    public void txTest() throws Exception {

        //开启事物
        Transaction multi = jedis.multi();

        try {

            int transfer = 50;
            multi.decrBy("user1_account", transfer);
            //int k = 10 / 0;
            multi.incrBy("user2_account", transfer);

            //提交事物
            multi.exec();
        } catch (Exception e) {
            e.printStackTrace();
            //回滚事物
            multi.discard();
        }

        System.out.format("user1_account：%s%n", jedis.get("user1_account"));
        System.out.format("user2_account：%s%n", jedis.get("user2_account"));

        jedis.close();
    }

    /**
     * 使用`watch key`监控指定数据，相当于乐观锁加锁
     */
    @Test
    public void watchTest() throws Exception {

        // watch命令用于监视一个(或多个) key ，如果在事务执行之前这个(或这些) key 被其他命令所改动，那么事务将被打断
        jedis.watch("user1_account", "user2_account");

        //打断点，debug,用命令行修改user1_account或者user2_account再往下执行；
        //开启事物
        Transaction multi = jedis.multi();

        try {

            int transfer = 50;
            multi.decrBy("user1_account", transfer);
            multi.incrBy("user2_account", transfer);

            //提交事物
            multi.exec();
        } catch (Exception e) {
            e.printStackTrace();
            //回滚事物
            multi.discard();
        }

        //exec和discard后会自己unwatch
        //jedis.unwatch();

        System.out.format("user1_account：%s%n", jedis.get("user1_account"));
        System.out.format("user2_account：%s%n", jedis.get("user2_account"));

        jedis.close();
    }
}