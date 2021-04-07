package cn.clboy.demo.spring.bean.lifecycle.config;


import cn.clboy.demo.spring.bean.lifecycle.pojo.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    public Person person() {
        return new Person();
    }
}