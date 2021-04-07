package cn.cbloy.demo.springboot.file.upload.fastdfs;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 文件上传-fastDfs
 * <p>
 * 测试：
 * <p>
 * 需要配置application.yaml: tracker-list
 * <p>
 * 访问 http://127.0.0.1:8080/upload.html
 */
@SpringBootApplication
public class DemoSpringbootFileUploadFastDfsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoSpringbootFileUploadFastDfsApplication.class, args);
    }
}