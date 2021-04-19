package cn.clboy.demo.rabbitmq.basic.work;

import cn.clboy.demo.rabbitmq.basic.utils.RabbitMQUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

class Task implements Runnable {

    private static String slow_consumer = "Consumer02";

    @Override
    public void run() {
        String consumer = Thread.currentThread().getName();
        try {
            Connection connection = RabbitMQUtil.getConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(RabbitMQUtil.WORK_QUEUE, true, false, false, null);

            // 一次接收的消息数，消费完接收的任务后再去获取，设置小一点，实现能者多劳
            channel.basicQos(1);

            // 监听队列，第二个参数：是否自动进行消息确认。
            channel.basicConsume(RabbitMQUtil.WORK_QUEUE, false, new DefaultConsumer(channel) {

                /**
                 * 消费者接收消息调用此方法
                 *
                 * @param consumerTag 消费者的标签，在channel.basicConsume()去指定
                 * @param envelope    消息包的内容，可从中获取消息id，消息routingkey，交换机，消息和 重传标志(收到消息失败后是否需要重新发送)
                 * @param properties
                 * @param body        消息体
                 * @throws IOException
                 */
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body);
                    System.out.println(consumer + "- 自动应答 - 收到消息：" + message);

                    // 模拟工作慢的消费者,延迟应答
                    if (slow_consumer.equals(consumer)) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    this.getChannel().basicAck(envelope.getDeliveryTag(), false);
                }
            });
            System.out.println(consumer + "：已启动");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}