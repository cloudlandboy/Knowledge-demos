package cn.clboy.demo.rabbitmq.basic.persistence;

import cn.clboy.demo.rabbitmq.basic.utils.RabbitMQUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 如果在消费者消费之前，MQ就宕机了，消息就没了。
 * 要将消息持久化，前提是：队列，Exchange都持久化
 */

public class UsePersistence {

    public void example() throws IOException, TimeoutException {
        Connection connection = RabbitMQUtil.getConnection();
        Channel channel = connection.createChannel();

        //交换机是否持久化，由durable参数决定
        channel.exchangeDeclare("demo_persistence_exchange", BuiltinExchangeType.FANOUT, true);

        //队列是否持久化，由durable参数决定
        channel.queueDeclare("demo_persistence_queue", true, false, false, null);

        //消息持久化，如果要在服务重启后保持消息的持久化，必须设置消息是持久化的标识，通过BasicProperties指定
        channel.basicPublish("demo_persistence_exchange", "", MessageProperties.PERSISTENT_TEXT_PLAIN, "message".getBytes());
    }
}