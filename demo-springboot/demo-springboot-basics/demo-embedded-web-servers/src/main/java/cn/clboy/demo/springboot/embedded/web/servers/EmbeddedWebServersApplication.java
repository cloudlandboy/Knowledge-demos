package cn.clboy.demo.springboot.embedded.web.servers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @Author clboy
 * @Date 2021/2/24 上午10:53
 * @Since 1.0.0
 */
@ServletComponentScan
@SpringBootApplication
public class EmbeddedWebServersApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmbeddedWebServersApplication.class, args);
    }

}