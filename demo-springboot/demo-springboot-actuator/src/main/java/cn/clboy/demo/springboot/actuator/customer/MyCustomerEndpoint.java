package cn.clboy.demo.springboot.actuator.customer;


import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Endpoint(id = "myCustomer")
public class MyCustomerEndpoint {

    LinkedHashMap<String, String> data = new LinkedHashMap<>();

    {
        data.put("github", "https://github.com/cloudlandboy");
        data.put("blog", "https://www.clboy.cn");
    }

    /**
     * get请求
     */
    @ReadOperation
    public Map<String, String> read() {
        return data;
    }

    /**
     * post请求
     */
    @WriteOperation
    public void write() {
        data.put(UUID.randomUUID().toString(), System.currentTimeMillis() + "");
    }

    /**
     * delete请求
     */
    @DeleteOperation
    public void delete(String id) {
        data.remove(id);
    }
}