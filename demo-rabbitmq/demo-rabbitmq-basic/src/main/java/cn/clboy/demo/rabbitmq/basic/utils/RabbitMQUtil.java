package cn.clboy.demo.rabbitmq.basic.utils;


import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQUtil {

    public static Connection getConnection() throws IOException, TimeoutException {
        //定义连接工厂,设置连接信息
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.46.88");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("root");
        connectionFactory.setPassword("root");
        connectionFactory.setVirtualHost("/demo-rabbitmq-basic");

        //获取连接
        Connection connection = connectionFactory.newConnection();
        return connection;
    }
}