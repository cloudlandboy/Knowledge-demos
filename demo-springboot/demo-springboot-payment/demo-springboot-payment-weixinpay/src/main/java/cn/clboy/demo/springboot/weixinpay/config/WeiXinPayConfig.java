package cn.clboy.demo.springboot.weixinpay.config;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(WeiXinPayProperties.class)
public class WeiXinPayConfig {

}