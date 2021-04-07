package cn.clboy.demo.springcloud.feign.provider.api;


import cn.clboy.demo.springcloud.feign.provider.pojo.TbUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author cloudlandboy
 * @Date 2019/11/25 下午5:05
 * @Since 1.0.0
 */

public interface UserControllerApi {

    @GetMapping("/user/{id}")
    TbUser queryUserById(@PathVariable Long id);
}