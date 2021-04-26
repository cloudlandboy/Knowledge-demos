package cn.clboy.demo.redis.springboot.single.config;


import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

public class KeyExpireMessageListener extends KeyExpirationEventMessageListener {


    public KeyExpireMessageListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    protected void doHandleMessage(Message message) {
        System.out.println("监听到key过期：");
        //过期的key
        String messageBody = new String(message.getBody());
        System.out.println(messageBody);
    }
}