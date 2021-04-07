package cn.clboy.demo.aop.proxy.jdk;

import cn.clboy.demo.aop.proxy.utils.Monitor;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author clboy
 * @Date 2021/2/25 下午3:08
 * @Since 1.0.0
 */

public class JdkAopTest {

    @Test
    public void Test() throws Exception {
        Person person = (Person) getProxyObj(Women.class);
        person.eat();
        String buy = person.shopping();
        System.out.println("买到" + buy);
    }


    public Object getProxyObj(Class targetClass) {
        return Proxy.newProxyInstance(targetClass.getClassLoader(), targetClass.getInterfaces(), (Object proxy, Method method, Object[] args) -> {
            Monitor.start();
            Object res = method.invoke(targetClass.newInstance(), args);
            System.out.println(method.getName() + " 执行时间为：" + Monitor.end());
            return res;
        });

    }
}