package cn.clboy.demo.springcloud.feign.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan(basePackages = "cn.clboy.demo.springcloud.ribbon.provider.mapper")
@EnableDiscoveryClient
@SpringBootApplication
public class SpringcloudFeignProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudFeignProviderApplication.class, args);
	}

}
