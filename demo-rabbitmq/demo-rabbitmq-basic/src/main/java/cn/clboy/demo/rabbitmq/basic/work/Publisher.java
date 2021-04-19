package cn.clboy.demo.rabbitmq.basic.work;


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
            // 声明一个队列(幂等的,只有当它不存在时才会被创建)
            channel.queueDeclare(RabbitMQUtil.WORK_QUEUE, true, false, false, null);


            /**
             * Exchange的名称，如果没有指定，则使用Default Exchange
             * routingKey,消息的路由Key，是用于Exchange（交换机）将消息转发到指定的消息队列
             * props:消息包含的属性
             * body：消息体
             */
            for (int i = 0; i < 20; i++) {
                String pre = i + "-->";
                String message = pre + LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss")) + "@hello rabbitMQ!";
                channel.basicPublish("", RabbitMQUtil.WORK_QUEUE, null, message.getBytes());
                System.out.println(pre + Publisher.class.getName() + "：消息已经发送");
            }
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }
}