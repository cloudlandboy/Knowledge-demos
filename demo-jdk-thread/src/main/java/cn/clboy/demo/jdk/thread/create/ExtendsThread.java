package cn.clboy.demo.jdk.thread.create;


import java.util.concurrent.FutureTask;

/**
 * 创建线程方式一
 * 继承Thread类重写run方法
 */
public class ExtendsThread extends Thread {

    @Override
    public void run() {
        System.out.println("ExtendsThread：");
        System.out.println(getName() + " do something......");
    }

    public static void main(String[] args) {
        // 启动线程
        ExtendsThread thread = new ExtendsThread();
        thread.start();
        System.out.println("main 线程");
    }
}