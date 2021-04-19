package cn.clboy.demo.rabbitmq.springboot;


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * springboot整合rabbitmq
 */
@EnableRabbit
@EnableSwagger2
@SpringBootApplication
public class DemoRabbitmqSpringbootApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(DemoRabbitmqSpringbootApplication.class, args);
        AmqpTemplate bean = applicationContext.getBean(AmqpTemplate.class);
    }

}