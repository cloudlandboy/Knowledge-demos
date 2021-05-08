package cn.clboy.demo.springboot.admin.client.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Configuration
@EnableScheduling
public class ScheduleConfig {


    /**
     * 下一个0秒开始（0/10/20/30/40/50）
     * 每过10秒执行一次
     *
     * @throws InterruptedException
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void task01() throws InterruptedException {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG));
        System.out.println("task01：" + now);
    }

    @Scheduled(fixedRate =5000)
    public void task02(){
        String now = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG));
        System.out.println("task02：" + now);
    }
}