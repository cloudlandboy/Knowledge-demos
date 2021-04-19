package cn.clboy.demo.jdk.thread.create;

/**
 * 创建线程方式二
 * 实现Runnable接口重写run方法
 */
public class ImplRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println("ImplRunnable：");
        System.out.println(Thread.currentThread().getName() + " do something......");
    }

    public static void main(String[] args) {
        //启动线程
        ImplRunnable runnable = new ImplRunnable();
        Thread thread = new Thread(runnable);
        thread.start();
        System.out.println("main 线程");
    }
}