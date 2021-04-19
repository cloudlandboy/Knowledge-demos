package cn.clboy.demo.rabbitmq.springboot.mq;


import cn.clboy.demo.rabbitmq.springboot.config.MQConfig;
import cn.clboy.demo.rabbitmq.springboot.pojo.VerifyCode;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class MessageListener {


    /**
     * 基本消息模型，Exchange的名称，如果没有指定，则使用Default Exchange，默认的是direct类型
     * <p>
     * 测试手动ack案例
     */

    @RabbitListener(queuesToDeclare = @Queue(MQConfig.BASIC_QUEUE), errorHandler = "rabbitListenerErrorHandler")
    public void listenBasic(VerifyCode code, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        //try {

            log.info("Basic接收到消息：" + code);

            //模拟异常
            if ("00000" .equals(code.getCode())) {
                throw new RuntimeException();
            }

            //ackm,项目中配置的是自动ack可省略
            //channel.basicAck(deliveryTag, false);
        //} catch (Exception e) {
        //    //unack
        //    channel.basicNack(deliveryTag, false, true);
        //}


    }


    /**
     * FANOUT/广播消息模型
     */
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue("demo_springboot_fanout_queue01"),
                    exchange = @Exchange(name = MQConfig.FANOUT_EXCHANGE, type = ExchangeTypes.FANOUT)
            )
    })
    public void listenFanout_q01(VerifyCode code) {
        log.info("fanout_queue01接收到消息：" + code);
    }

    /**
     * FANOUT/广播消息模型
     */
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue("demo_springboot_fanout_queue02"),
                    exchange = @Exchange(name = MQConfig.FANOUT_EXCHANGE, type = ExchangeTypes.FANOUT)
            )
    })
    public void listenFanout_q02(VerifyCode code) {
        log.info("fanout_queue02接收到消息：" + code);
    }


    /**
     * DIRECT/路由消息模型
     */
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue("demo_springboot_direct_email"),
                    exchange = @Exchange(name = MQConfig.DIRECT_EXCHANGE, type = ExchangeTypes.DIRECT),
                    key = "email"
            )
    })
    public void listenDirect_email(VerifyCode code) {
        log.info("direct_email接收到消息：" + code);
    }


    /**
     * DIRECT/路由消息模型
     */
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue("demo_springboot_direct_phone"),
                    exchange = @Exchange(name = MQConfig.DIRECT_EXCHANGE, type = ExchangeTypes.DIRECT),
                    key = "phone"
            )
    })
    public void listenDirect_phone(VerifyCode code) {
        log.info("direct_phone接收到消息：" + code);
    }

    /**
     * TOPIC/通配符路由消息模型
     * 监听所有add消息
     */
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue("demo_springboot_topic_all_add"),
                    exchange = @Exchange(name = MQConfig.TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC),
                    key = "#.add"
            )
    })
    public void listenTopic_allAdd(VerifyCode code) {
        log.info("topic_ #.add接收到消息：" + code);
    }

    /**
     * TOPIC/通配符路由消息模型
     * 监听user.role的消息
     */
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue("demo_springboot_topic_user_role"),
                    exchange = @Exchange(name = MQConfig.TOPIC_EXCHANGE, type = ExchangeTypes.TOPIC),
                    key = "user.role.*"
            )
    })
    public void listenTopic_userRole(VerifyCode code) {
        log.info("topic_ user.role.*接收到消息：" + code);
    }
}