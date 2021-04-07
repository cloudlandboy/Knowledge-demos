package cn.clboy.demo.rabbitmq.basic.basic;


import cn.clboy.demo.rabbitmq.basic.utils.RabbitMQUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息监听者/接收者
 */
public class Recver {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMQUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(Publisher.QUEUE_NAME, true, false, false, null);
        handAck(channel);
    }

    /**
     * 自动应答
     *
     * @param channel
     * @throws IOException
     */
    public static void autoAck(Channel channel) throws IOException {
        // 监听队列，第二个参数：是否自动进行消息确认。
        channel.basicConsume(Publisher.QUEUE_NAME, true, new DefaultConsumer(channel) {

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
                System.out.println(Recver.class.getName() + "- 自动应答 - 收到消息：" + message);
                System.out.println("处理完成！");
            }
        });
    }

    /**
     * 手动应答
     *
     * @param channel
     * @throws IOException
     */
    public static void handAck(Channel channel) throws IOException {
        // 监听队列，第二个参数：是否自动进行消息确认。
        channel.basicConsume(Publisher.QUEUE_NAME, false, new DefaultConsumer(channel) {

            /**
             * 消费者接收消息调用此方法
             * @param consumerTag 消费者的标签，在channel.basicConsume()去指定
             * @param envelope 消息包的内容，可从中获取消息id，消息routingkey，交换机，消息和重传标志(收到消息失败后是否需要重新发送)
             * @param properties
             * @param body  消息体
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body);
                System.out.println(Recver.class.getName() + "- 手动应答 - 收到消息：" + message);
                System.out.println("处理完成：查看后台消息任然存在，再次运行还会监听到!");
                //手动ack
                this.getChannel().basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}