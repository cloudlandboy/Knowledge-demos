package cn.clboy.demo.springcloud.microservice.simulation.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("cn.clboy.demo.springcloud.microservice.simulation.provider.mapper")
@SpringBootApplication
public class SpringcloudMicroServiceSimulationProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudMicroServiceSimulationProviderApplication.class, args);
    }

}
