package cn.clboy.demo.jdk.thread.lock.mechanism;


import org.openjdk.jol.info.ClassLayout;

/**
 * 锁机制
 */
public class LockMechanismTest {

    public static void main(String[] args) {

        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        synchronized (o) {
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }

        System.out.println(ClassLayout.parseInstance(o).toPrintable());

    }
}