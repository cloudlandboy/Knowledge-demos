package cn.clboy.demo.rabbitmq.basic.subject.topic;


import cn.clboy.demo.rabbitmq.basic.utils.RabbitMQUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Topic类型的Exchange与Direct相比，都是可以根据RoutingKey把消息路由到不同的队列
 * 只不过Topic类型Exchange可以让队列在绑定Routing key 的时候使用通配符！
 * `#`：匹配一个或多个词
 * `*`：匹配不多不少恰好1个词
 * <p>
 * 例：
 * `audit.#`：能够匹配`audit.irs.corporate` 或者 `audit.irs`
 * `audit.*`：只能匹配`audit.irs`
 */
public class Publisher {


    public static void main(String[] args) {
        try (
                //获取连接
                Connection connection = RabbitMQUtil.getConnection();
                //创建通道
                Channel channel = connection.createChannel();
        ) {

            // 订阅/发布模式 生产者不再指定队列，只声明交换机，消息发送到交换机，由交换机决定发送到哪个队列
            channel.exchangeDeclare(RabbitMQUtil.TOPIC_EXCHANGE, BuiltinExchangeType.TOPIC);


            for (int i = 0; i < 10; i++) {
                String routingKey = "user.role.add";
                if (i % 2 == 0) {
                    routingKey = "user.role.del";
                } else if (i == 7) {
                    routingKey = "user.permission.add";
                }
                String message = i + " hello rabbitMQ! -->" + routingKey;
                channel.basicPublish(RabbitMQUtil.TOPIC_EXCHANGE, routingKey, null, message.getBytes());
                System.out.println(routingKey + "：消息已经发送");
            }
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }
}