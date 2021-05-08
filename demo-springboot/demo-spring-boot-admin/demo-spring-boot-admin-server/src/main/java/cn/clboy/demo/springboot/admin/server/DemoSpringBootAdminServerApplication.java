package cn.clboy.demo.springboot.admin.server;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *  EnableAdminServer：开启spring-boot-admin-server程序
 *
 *  启动后直接访问 http://127.0.0.1:9090
 */
@EnableAdminServer
@SpringBootApplication
public class DemoSpringBootAdminServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoSpringBootAdminServerApplication.class, args);
	}

}
