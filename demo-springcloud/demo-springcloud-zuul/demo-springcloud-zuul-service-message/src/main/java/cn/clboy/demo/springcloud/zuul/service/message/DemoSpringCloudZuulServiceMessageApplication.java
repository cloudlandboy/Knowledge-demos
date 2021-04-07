package cn.clboy.demo.springcloud.zuul.service.message;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@EnableDiscoveryClient
public class DemoSpringCloudZuulServiceMessageApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoSpringCloudZuulServiceMessageApplication.class, args);
    }
}