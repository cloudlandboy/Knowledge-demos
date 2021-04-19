package cn.clboy.demo.rabbitmq.basic.subject.fanout;


import cn.clboy.demo.rabbitmq.basic.utils.RabbitMQUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeoutException;

/**
 * 在广播模式下，消息发送流程是这样的：
 *  可以有多个消费者
 *  每个消费者有自己的queue（队列）
 *  每个队列都要绑定到Exchange（交换机）
 *  生产者发送的消息，只能发送到交换机，交换机来决定要发给哪个队列，生产者无法决定。
 *  交换机把消息发送给绑定过的所有队列
 *  队列的消费者都能拿到消息。实现一条消息被多个消费者消费
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
            channel.exchangeDeclare(RabbitMQUtil.FANOUT_EXCHANGE, BuiltinExchangeType.FANOUT);

            String message = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss")) + "@hello rabbitMQ!";

            /**
             * Exchange的名称，如果没有指定，则使用Default Exchange
             * routingKey,消息的路由Key，是用于Exchange（交换机）将消息转发到指定的消息队列
             * props:消息包含的属性
             * body：消息体
             */
            channel.basicPublish(RabbitMQUtil.FANOUT_EXCHANGE, "", null, message.getBytes());
            System.out.println(Publisher.class.getName() + "：消息已经发送");
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }
}