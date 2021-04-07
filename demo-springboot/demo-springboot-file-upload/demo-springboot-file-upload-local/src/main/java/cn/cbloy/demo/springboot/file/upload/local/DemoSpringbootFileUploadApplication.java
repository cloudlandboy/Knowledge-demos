package cn.cbloy.demo.springboot.file.upload.local;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 文件上传,启动访问 http://127.0.0.1:8080/upload.html
 */
@SpringBootApplication
public class DemoSpringbootFileUploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoSpringbootFileUploadApplication.class, args);
    }
}