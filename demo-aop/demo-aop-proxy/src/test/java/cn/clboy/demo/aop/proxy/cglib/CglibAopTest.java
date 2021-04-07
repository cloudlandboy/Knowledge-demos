package cn.clboy.demo.aop.proxy.cglib;

import cn.clboy.demo.aop.proxy.utils.Monitor;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @Author clboy
 * @Date 2021/2/25 下午8:00
 * @Since 1.0.0
 */

public class CglibAopTest {

    @Test
    public void test() throws Exception {
        Women women = (Women) getProxyObj(Women.class);
        women.eat();
        women.shopping();
    }

    public Object getProxyObj(Class targetClass) {
        //中文意思:"增强剂/增强者"
        Enhancer enhancer = new Enhancer();
        //设置父类(被代理的目标类)
        enhancer.setSuperclass(targetClass);
//
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                Monitor.start();
                Object res = proxy.invokeSuper(obj, args);
                System.out.println(method.getName() + " 执行时间为：" + Monitor.end());
                return res;
            }
        });

        return enhancer.create();
    }

}