package cn.clboy.demo.rabbitmq.basic.basic;


import cn.clboy.demo.rabbitmq.basic.utils.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeoutException;

/**
 * 消息生产者
 */
public class Publisher {


    public static void main(String[] args) {
        try (
                //获取连接
                Connection connection = RabbitMQUtil.getConnection();
                //创建通道
                Channel channel = connection.createChannel();
        ) {
            // 声明一个队列(幂等的,只有当它不存在时才会被创建).参数：(队列名, 是否持久化,队列是否独占此连接,队列不再使用时是否自动删除此队列,队列参数)
            channel.queueDeclare(RabbitMQUtil.BASIC_QUEUE, true, false, false, null);

            String message = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss")) + "@hello rabbitMQ!";

            /**
             * Exchange的名称，如果没有指定，则使用Default Exchange
             * routingKey,消息的路由Key，是用于Exchange（交换机）将消息转发到指定的消息队列
             * props:消息包含的属性
             * body：消息体
             */
            channel.basicPublish("", RabbitMQUtil.BASIC_QUEUE, null, message.getBytes());
            System.out.println(Publisher.class.getName() + "：消息已经发送");
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }
}