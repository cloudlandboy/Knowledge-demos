package cn.clboy.demo.rabbitmq.basic.work;


import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息监听者/接收者
 */
public class Consumer02 {

    public static void main(String[] args) throws IOException, TimeoutException {
        new Thread(new Task(), "Consumer02").start();
    }
}