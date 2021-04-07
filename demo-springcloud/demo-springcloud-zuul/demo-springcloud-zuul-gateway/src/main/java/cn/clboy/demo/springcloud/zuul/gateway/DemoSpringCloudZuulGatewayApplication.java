package cn.clboy.demo.springcloud.zuul.gateway;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * EnableZuulProxy：开启Zuul的功能
 */
@EnableZuulProxy
@SpringBootApplication
public class DemoSpringCloudZuulGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoSpringCloudZuulGatewayApplication.class, args);
    }
}