package cn.clboy.demo.rabbitmq.basic.subject.direct;


import cn.clboy.demo.rabbitmq.basic.utils.RabbitMQUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 在Direct模型下，队列与交换机的绑定，不能是任意绑定了，而是要指定一个RoutingKey（路由key）
 * 消息的发送方在向Exchange发送消息时，也必须指定消息的routing key
 * Exchange（交换机），接收生产者的消息，然后把消息递交给 与routing key完全匹配的队列
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
            channel.exchangeDeclare(RabbitMQUtil.DIRECT_EXCHANGE, BuiltinExchangeType.DIRECT);


            for (int i = 0; i < 10; i++) {
                String routingKey = "email";
                if (i % 2 == 0) {
                    routingKey = "message";
                } else if (i == 7) {
                    routingKey = "qq";
                }
                String message = i + " hello rabbitMQ! -->" + routingKey;
                channel.basicPublish(RabbitMQUtil.DIRECT_EXCHANGE, routingKey, null, message.getBytes());
                System.out.println(routingKey + "：消息已经发送");
            }
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }
}