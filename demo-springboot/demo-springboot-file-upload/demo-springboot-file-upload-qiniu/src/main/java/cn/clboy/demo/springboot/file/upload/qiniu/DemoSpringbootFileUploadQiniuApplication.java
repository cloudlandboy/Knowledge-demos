package cn.clboy.demo.springboot.file.upload.qiniu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 文件上传 - 七牛云
 *
 * <p>
 * 测试：
 * <p>
 * 需要配置application.yaml,几个必须配置的选项：
 * <pre>
 *  upload:
 *      qiniu:
 *          access-key
 *          secret-keyx
 *          bucket
 *          domain
 *          region
 * </pre>
 * <p>
 * 访问 http://127.0.0.1:8080/upload.html
 */
@SpringBootApplication
public class DemoSpringbootFileUploadQiniuApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoSpringbootFileUploadQiniuApplication.class, args);
    }
}