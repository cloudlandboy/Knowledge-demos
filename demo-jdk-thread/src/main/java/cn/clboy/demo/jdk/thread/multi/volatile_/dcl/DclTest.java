package cn.clboy.demo.jdk.thread.multi.volatile_.dcl;


import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class DclTest {

    public static void main(String[] args) {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(25, 25, 0, TimeUnit.MINUTES, new LinkedBlockingQueue<>());
        for (int i = 0; i < 25; i++) {
            threadPoolExecutor.submit(() -> {
                System.out.println(DclDemo.getInstance());
            });
        }
    }
}