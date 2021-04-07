package cn.clboy.demo.springcloud.feign.consumer.client;


import cn.clboy.demo.springcloud.feign.provider.api.UserControllerApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;

@Primary
@FeignClient(name = "demo-springcloud-feign-provider", fallback = UserClientFallback.class)
public interface UserClient extends UserControllerApi {
}