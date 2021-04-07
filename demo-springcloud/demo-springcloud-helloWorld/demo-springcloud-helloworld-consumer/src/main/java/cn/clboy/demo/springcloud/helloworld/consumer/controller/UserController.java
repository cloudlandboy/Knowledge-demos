package cn.clboy.demo.springcloud.helloworld.consumer.controller;

import cn.clboy.demo.springcloud.helloworld.consumer.pojo.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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
        // 根据服务名称，获取服务实例。有可能是集群，所以是service实例集合
        List<ServiceInstance> instances = discoveryClient.getInstances("demo-springcloud-helloworld-provider");
        // 因为只有一个Service-provider。所以这里直接获取第一个实例
        ServiceInstance serviceInstance = instances.get(0);
        //从实例中获取主机和端口号拼接出接口地址
        return restTemplate.getForObject("http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/user/" + id, TbUser.class);
    }
}