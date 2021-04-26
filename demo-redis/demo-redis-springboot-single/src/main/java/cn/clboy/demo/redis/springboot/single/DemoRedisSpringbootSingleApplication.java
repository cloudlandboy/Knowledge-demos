package cn.clboy.demo.redis.springboot.single;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * springboot 2.x后 ，原来使用的 Jedis 被 lettuce 替换
 * 项目中不存再jedis相关的依赖，Jedis是不生效的
 * <p>
 * EnableCaching：开启缓存注解
 */
@EnableCaching
@SpringBootApplication
public class DemoRedisSpringbootSingleApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoRedisSpringbootSingleApplication.class, args);
    }

}
