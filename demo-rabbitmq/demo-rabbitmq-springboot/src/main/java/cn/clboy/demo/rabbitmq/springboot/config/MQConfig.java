package cn.clboy.demo.rabbitmq.springboot.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbotMQ 配置类
 */
@Slf4j
@Configuration
public class MQConfig implements InitializingBean {


    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 使用amqpAdmin编码的方式配置绑定队列等信息（用于先启动发布者的时候创建）
     * <p>
     * 这里为了便于测试就将发布者和消费者都弄到一个项目里了，用消费者的注解创建，因此下面的内部类类注不注册都行
     */
    public static final String ENABLE = "false";


    /**
     * 使用json消息转换器，默认是jdk序列化的方式
     * <p>
     * 注意别导错包
     *
     * @see org.springframework.amqp.support.converter.MessageConverter
     */
    @Bean
    public MessageConverter rabbitMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    /**
     * 处理RabbitListener 消费者方法抛出的错误
     */
    @Bean
    public RabbitListenerErrorHandler rabbitListenerErrorHandler() {
        return new RabbitListenerErrorHandler() {
            @Override
            public Object handleError(Message amqpMessage, org.springframework.messaging.Message<?> message, ListenerExecutionFailedException exception) throws Exception {
                log.info("出错了！");
                Object payload = message.getPayload();
                log.info("消息体：{}", payload.toString());
                log.info("异常信息：{}", exception.toString());
                //TODO 返回其他值会进入死循环，返回null值会ack消息
                return null;
            }
        };
    }


    @Override
    public void afterPropertiesSet() throws Exception {

        /**
         * 发布者确认回调,通过实现 ConfirmCallback 接口，消息发送到 Broker 后触发回调，确认消息是否到达 Broker 服务器
         * 也就是只确认是否正确到达 Exchange 中
         *
         * 每一条发出的消息都会调用ConfirmCallback；
         *
         * 需要在配置文件中开启，publisher-confirms
         */
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {

            /**
             * @param correlationData
             * @param ack 为false就是消息发送失败
             * @param cause
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("====ConfirmCallback：====");
                if (null != correlationData) {
                    log.info("id：{},message：{}", correlationData.getId(), correlationData.getReturnedMessage());
                }
                log.info("ack：{}，cause：{}", ack, cause);
                log.info("====end====");
            }
        });

        /**
         *  返回消息的回调,只有在消息进入exchange但没有进入queue时才会调用
         *
         *  需要在配置文件中开启，publisher-returns
         */
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.info("====ReturnCallback：====");
                log.info("message：{}", message);
                log.info("replyText：{}", replyText);
                log.info("exchange：{}，routingKey：{}", exchange, routingKey);
                log.info("====end====");
            }
        });
    }


    /**
     * 创建一个没有绑定队列的交换机用于测试
     */
    @Bean
    public Exchange unBindExchange() {
        Exchange exchange = ExchangeBuilder.directExchange("un_bind_exchange").build();
        amqpAdmin.declareExchange(exchange);
        return exchange;
    }


    public static final String BASIC_QUEUE = "demo_springboot_basic_queue";
    public static final String FANOUT_EXCHANGE = "demo_springboot_fanout_exchange";
    public static final String DIRECT_EXCHANGE = "demo_springboot_direct_exchange";
    public static final String TOPIC_EXCHANGE = "demo_springboot_topic_exchange";


    @Configuration
    @ConditionalOnExpression(MQConfig.ENABLE)
    public class BasicRabbitConfig {

        @Bean
        public Queue basicQueue() {
            return new Queue(BASIC_QUEUE, true);
        }

        @Bean
        public Binding basicQueueBinding() {
            Binding binding = BindingBuilder.bind(basicQueue()).to(DirectExchange.DEFAULT).withQueueName();
            amqpAdmin.declareBinding(binding);
            return binding;
        }

    }

    @Configuration
    @ConditionalOnExpression(MQConfig.ENABLE)
    public class fanoutRabbitConfig {

        @Bean
        public Exchange fanoutExchange() {
            Exchange fanoutExchange = ExchangeBuilder.fanoutExchange(FANOUT_EXCHANGE).durable(true).build();
            amqpAdmin.declareExchange(fanoutExchange);
            return fanoutExchange;
        }
    }

    @Configuration
    @ConditionalOnExpression(MQConfig.ENABLE)
    public class directRabbitConfig {

        @Bean
        public Exchange directExchange() {
            Exchange directExchange = ExchangeBuilder.directExchange(DIRECT_EXCHANGE).durable(true).build();
            amqpAdmin.declareExchange(directExchange);
            return directExchange;
        }
    }

    @Configuration
    @ConditionalOnExpression(MQConfig.ENABLE)
    public class topicRabbitConfig {

        @Bean
        public Exchange topicExchange() {
            Exchange topicExchange = ExchangeBuilder.topicExchange(TOPIC_EXCHANGE).durable(true).build();
            amqpAdmin.declareExchange(topicExchange);
            return topicExchange;
        }
    }
}