package cn.clboy.demo.rabbitmq.basic.subject.direct;


import cn.clboy.demo.rabbitmq.basic.utils.RabbitMQUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息监听者/接收者
 */
public class MessageRecver02 {

    private static final String QUEUE_NAME = "demo_direct_queue_message";
    private static final String ROUTINGKEY = "message";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMQUtil.getConnection();
        Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        //声明交换机
        channel.exchangeDeclare(RabbitMQUtil.DIRECT_EXCHANGE, BuiltinExchangeType.DIRECT);
        //将队列绑定到交换机
        channel.queueBind(QUEUE_NAME, RabbitMQUtil.DIRECT_EXCHANGE, ROUTINGKEY);

        // 监听队列，第二个参数：是否自动进行消息确认。
        channel.basicConsume(QUEUE_NAME, true, new DefaultConsumer(channel) {

            /**
             * 消费者接收消息调用此方法
             * @param consumerTag 消费者的标签，在channel.basicConsume()去指定
             * @param envelope 消息包的内容，可从中获取消息id，消息routingkey，交换机，消息和 重传标志(收到消息失败后是否需要重新发送)
             * @param properties
             * @param body  消息体
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body);
                System.out.println(ROUTINGKEY + "- 自动应答 - 收到消息：" + message);
            }
        });
    }
}