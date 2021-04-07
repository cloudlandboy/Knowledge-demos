package cn.clboy.demo.aop.proxy.jdk;

/**
 * @Author clboy
 * @Date 2021/2/25 下午3:24
 * @Since 1.0.0
 */

public class Women implements Person {

    @Override
    public void eat() throws Exception {
        System.out.println("吃饭中...");
        Thread.sleep(2000);
    }

    @Override
    public String shopping() throws Exception {
        System.out.println("逛街中...");
        Thread.sleep(5000);
        return "kity猫";
    }
}