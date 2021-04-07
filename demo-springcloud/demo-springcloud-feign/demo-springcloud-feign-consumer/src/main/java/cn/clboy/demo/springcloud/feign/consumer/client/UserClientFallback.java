package cn.clboy.demo.springcloud.feign.consumer.client;


import cn.clboy.demo.springcloud.feign.provider.pojo.TbUser;
import org.springframework.stereotype.Component;

@Component
public class UserClientFallback implements UserClient {

    @Override
    public TbUser queryUserById(Long id) {
        return new TbUser();
    }
}