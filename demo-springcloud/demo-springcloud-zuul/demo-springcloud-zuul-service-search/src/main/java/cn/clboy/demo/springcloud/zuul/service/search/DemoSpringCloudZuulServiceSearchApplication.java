package cn.clboy.demo.springcloud.zuul.service.search;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DemoSpringCloudZuulServiceSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoSpringCloudZuulServiceSearchApplication.class, args);
    }
}