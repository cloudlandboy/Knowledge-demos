package cn.clboy.demo.shiro.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author clboy
 * @Date 2021/2/7 下午4:40
 * @Since 1.0.0
 */

@SpringBootApplication
@MapperScan("cn.clboy.demo.shiro.springboot.mapper")
public class DemoShiroSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoShiroSpringbootApplication.class, args);
    }
}