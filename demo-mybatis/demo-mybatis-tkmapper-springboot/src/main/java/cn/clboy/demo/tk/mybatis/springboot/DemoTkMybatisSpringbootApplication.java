package cn.clboy.demo.tk.mybatis.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 采用 "Free Mybatis plugin" 插件逆向生成的 mapper
 */
@MapperScan(basePackages = "cn.clboy.demo.tk.mybatis.springboot.mapper")
@SpringBootApplication
public class DemoTkMybatisSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoTkMybatisSpringbootApplication.class, args);
    }

}
