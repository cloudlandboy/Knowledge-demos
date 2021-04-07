package cn.clboy.demo.springboot.task;


import cn.clboy.demo.springboot.task.async.AsyncOnClass;
import cn.clboy.demo.springboot.task.async.AsyncOnMethod;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * springBoot的异步方法和定时任务
 */
@EnableAsync
@SpringBootApplication
public class DemoSpringbootTaskApplication {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(DemoSpringbootTaskApplication.class, args);
        //testAsyncOnClass(context);
        //testAsyncOnMethod(context);
    }

    public static void testAsyncOnClass(ApplicationContext context) throws InterruptedException {
        AsyncOnClass asyncOnClass = context.getBean(AsyncOnClass.class);
        asyncOnClass.m1();
        ListenableFuture<String> future = asyncOnClass.m2();
        future.addCallback(System.out::println, err -> System.err.println("callback：" + err.getMessage()));
        System.out.println(DemoSpringbootTaskApplication.class.getName() + ".testAsyncOnClass() print");
    }

    public static void testAsyncOnMethod(ApplicationContext context) throws InterruptedException {
        AsyncOnMethod asyncOnMethod = context.getBean(AsyncOnMethod.class);
        asyncOnMethod.m1();
        ListenableFuture<String> future = asyncOnMethod.m2();
        future.addCallback(System.out::println, err -> System.err.println("callback：" + err.getMessage()));
        System.out.println(DemoSpringbootTaskApplication.class.getName() + ".testAsyncOnMethod() print");
    }
}