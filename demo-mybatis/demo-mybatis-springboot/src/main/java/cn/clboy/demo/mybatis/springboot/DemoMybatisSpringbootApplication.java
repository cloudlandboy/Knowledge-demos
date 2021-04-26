package cn.clboy.demo.mybatis.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 采用 "Free Mybatis plugin" 插件逆向生成的 mapper
 */
@MapperScan(basePackages = "cn.clboy.demo.mybatis.springboot.mapper")
@SpringBootApplication
public class DemoMybatisSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoMybatisSpringbootApplication.class, args);
    }

}
