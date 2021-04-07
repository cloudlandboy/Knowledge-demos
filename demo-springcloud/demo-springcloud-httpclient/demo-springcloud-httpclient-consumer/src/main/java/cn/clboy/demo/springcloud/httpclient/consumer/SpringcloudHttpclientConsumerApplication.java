package cn.clboy.demo.springcloud.httpclient.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SpringcloudHttpclientConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudHttpclientConsumerApplication.class, args);
    }

    @Bean
    public RestTemplate resttemplate() {
        return new RestTemplate();
    }
}
