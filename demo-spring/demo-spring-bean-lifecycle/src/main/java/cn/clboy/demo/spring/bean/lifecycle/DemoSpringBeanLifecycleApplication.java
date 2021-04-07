package cn.clboy.demo.spring.bean.lifecycle;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * spring bean的生命周期
 */
public class DemoSpringBeanLifecycleApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("cn.clboy.demo.spring.bean.lifecycle");
    }
}