package cn.clboy.demo.shiro.springboot.configuration;

import cn.clboy.demo.shiro.springboot.utils.PasswordHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author clboy
 * @Date 2021/2/23 下午4:44
 * @Since 1.0.0
 */
@Configuration
public class BeanConfiguration {

    @Bean
    public PasswordHelper PasswordHelper() {
        PasswordHelper passwordHelper = new PasswordHelper();
        return passwordHelper;
    }
}