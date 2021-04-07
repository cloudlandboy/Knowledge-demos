package cn.clboy.demo.springcloud.zuul.service.message.helper;


import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Async
@Component
public class EmailHelper {

    public void sendEmail(String msg, String receiver) throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("邮件已发送给：" + receiver);
    }
}