package cn.clboy.demo.redis.basic.single.jedis.pub_sub;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class Subscriber {

    public static void main(String[] args) {
        //模拟3个监听者

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                Jedis jedis = new Jedis("127.0.0.1", 6379);

                //阻塞
                jedis.subscribe(new JedisPubSub() {
                    @Override
                    public void onMessage(String channel, String message) {
                        System.out.format("%s 收到 %s 的推送消息：%s%n",
                                Thread.currentThread().getName(),
                                channel, message);
                    }
                }, "taobao.weixin.public");

                System.out.println("==============end");
            }, "用户" + i).start();
        }
    }
}