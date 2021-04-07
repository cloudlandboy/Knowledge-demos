package cn.clboy.demo.springcloud.helloworld.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SpringcloudHelloworldEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudHelloworldEurekaServerApplication.class, args);
    }

}
