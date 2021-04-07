package cn.clboy.demo.springcloud.feign.eureka.server;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SpringcloudFeignEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudFeignEurekaServerApplication.class, args);
    }
}