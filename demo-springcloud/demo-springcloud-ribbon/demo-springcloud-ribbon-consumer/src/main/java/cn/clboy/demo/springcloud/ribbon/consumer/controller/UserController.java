package cn.clboy.demo.springcloud.ribbon.consumer.controller;

import cn.clboy.demo.springcloud.ribbon.consumer.pojo.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author cloudlandboy
 * @Date 2019/11/26 下午8:29
 * @Since 1.0.0
 */

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * eureka客户端，可以获取到eureka中服务的信息
     */
    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/{id}")
    public TbUser getUserById(@PathVariable Long id) {
        //从实例中获取主机和端口号拼接出接口地址
        String baseUrl = "http://demo-springcloud-ribbon-provider/user/";
        return restTemplate.getForObject(baseUrl + id, TbUser.class);
    }
}