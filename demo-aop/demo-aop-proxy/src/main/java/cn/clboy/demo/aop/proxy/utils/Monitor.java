package cn.clboy.demo.aop.proxy.utils;

/**
 * @Author clboy
 * @Date 2021/2/25 下午3:10
 * @Since 1.0.0
 */

public class Monitor {
    private static final ThreadLocal<Long> EXECUTIONTIME = new ThreadLocal<>();

    public static void start() {
        EXECUTIONTIME.set(System.currentTimeMillis());
    }

    public static long end() {
        Long totalSpend = System.currentTimeMillis() - EXECUTIONTIME.get();
        EXECUTIONTIME.set(totalSpend);
        return totalSpend;
    }

    public static long totalSpendTime() {
        return EXECUTIONTIME.get();
    }
}