package cn.clboy.demo.jdk.thread.create;

import java.util.concurrent.*;

/**
 * 创建线程方式三
 * 实现Runnable接口重写call方法
 */
public class ImplCallable implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("ImplCallable：");
        System.out.println(Thread.currentThread().getName() + " do something......");
        Thread.sleep(3000);
        return "执行完毕的返回值";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        ImplCallable callable = new ImplCallable();
        Future<String> future = executorService.submit(callable);
        System.out.println("main 线程");

        System.out.println(future.get());
        executorService.shutdown();

    }
}