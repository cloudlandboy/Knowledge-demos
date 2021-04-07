package cn.clboy.demo.springboot.task.async;


import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;


@Component
public class AsyncOnMethod {

    @Async
    public void m1() throws InterruptedException {
        System.out.println(this.getClass().getName() + ".m1() start");
        Thread.sleep(6000);
        System.out.println(this.getClass().getName() + ".m1() finish");

    }

    public ListenableFuture<String> m2() throws InterruptedException {
        System.out.println(this.getClass().getName() + ".m2() start");
        Thread.sleep(8000);
        System.out.println(this.getClass().getName() + ".m2() finish");
        return new AsyncResult<>(this.getClass().getName() + ".m2() callback");
    }
}