package cn.clboy.demo.jdk.thread.multi.volatile_;

/**
 * Volatile关键字
 * volatile用于保证数据的同步，也就是可见性
 * volatile不能保证原子性
 */
public class VolatileTest {
    private static boolean flag = true;
    //private static volatile boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            System.out.println("thread start");
            while (VolatileTest.flag) {
            }
            System.out.println("thread dead");
        }).start();

        Thread.sleep(1000);
        //修改flag
        flag = false;
    }

}