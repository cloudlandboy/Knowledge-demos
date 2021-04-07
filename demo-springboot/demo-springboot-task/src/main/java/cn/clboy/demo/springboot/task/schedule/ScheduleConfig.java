package cn.clboy.demo.springboot.task.schedule;


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
        //TEST： 因为是单线程执行定时任务，执行一次后，后面的会被阻塞无法执行，见@EnableScheduling注解文档
        Thread.sleep(999999999);
    }


    /**
     * 下一个0秒开始（0/10/20/30/40/50）
     * 每过5秒执行一次
     *
     * @throws InterruptedException
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void task02() throws InterruptedException {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG));
        System.out.println("task02：" + now);
    }
}