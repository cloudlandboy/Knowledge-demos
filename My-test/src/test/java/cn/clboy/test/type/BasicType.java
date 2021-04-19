package cn.clboy.test.type;


import org.junit.Test;

/**
 * 基本数据类型
 */
public class BasicType {

    /**
     * 比int的范围小的数值运算会被转为int,需要强转回来
     */
    @Test
    public void ltInt() {
        byte a = 10;
        byte b = 20;
        byte c = (byte) (a + b);
        System.out.println(c);

        short d = 50;
        short e = (short) (a + d);
        System.out.println(e);
    }

    /**
     * 不同类型运算会默认转为高类型
     */
    @Test
    public void diffType() {
        int a = 500;
        long b = 7200;
        long c = a + b;
        System.out.println(c);
    }
}