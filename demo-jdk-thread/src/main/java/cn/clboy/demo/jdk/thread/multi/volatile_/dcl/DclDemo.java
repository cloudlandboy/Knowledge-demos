package cn.clboy.demo.jdk.thread.multi.volatile_.dcl;


/**
 * DCL(Double Check Lock)：双重检测锁
 * 一般用于懒汉单例模式
 */
public class DclDemo {

    //饿汉试，启动时就创建，非常浪费资源
    //private static DclDemo dclDemo1 = new DclDemo();

    private static volatile DclDemo instance;

    public static DclDemo getInstance() {
        //第一层锁，减少不必要的加锁等待
        if (null == instance) {
            synchronized (DclDemo.class) {
                if (null == instance) {
                    instance = new DclDemo();
                }
            }
        }
        return instance;
    }


    private DclDemo() {

    }
}