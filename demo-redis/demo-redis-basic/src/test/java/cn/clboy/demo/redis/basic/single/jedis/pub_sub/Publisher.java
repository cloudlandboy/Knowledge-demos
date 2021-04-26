package cn.clboy.demo.redis.basic.single.jedis.pub_sub;


import redis.clients.jedis.Jedis;

import java.util.Timer;
import java.util.TimerTask;

public class Publisher {

    public static void main(String[] args) {

        Jedis jedis = new Jedis("127.0.0.1", 6379);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                jedis.publish("taobao.weixin.public", "大家好，某某商品又优惠啦......\n");
            }
        }, 2000, 5000);

        System.out.println("start");
    }
}