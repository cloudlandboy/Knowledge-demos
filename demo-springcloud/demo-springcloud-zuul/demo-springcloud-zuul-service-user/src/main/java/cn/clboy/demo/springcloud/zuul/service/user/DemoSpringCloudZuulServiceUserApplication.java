package cn.clboy.demo.springcloud.zuul.service.user;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DemoSpringCloudZuulServiceUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoSpringCloudZuulServiceUserApplication.class, args);
    }
}