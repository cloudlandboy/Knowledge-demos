package cn.clboy.demo.springcloud.zuul.registry;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DemoSpringCloudZuulRegistryApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoSpringCloudZuulRegistryApplication.class, args);
    }
}