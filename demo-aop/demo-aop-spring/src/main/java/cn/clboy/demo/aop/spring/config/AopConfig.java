package cn.clboy.demo.aop.spring.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


/**
 * 标注@Configuration的类等同于XML
 */
@Configuration
@ComponentScan(basePackages = "cn.clboy.demo.aop.spring")
@EnableAspectJAutoProxy
public class AopConfig {

}