package cn.clboy.demo.springcloud.microservice.simulation.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SpringcloudMicroServiceSimulationConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudMicroServiceSimulationConsumerApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
