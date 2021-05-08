package cn.clboy.demo.springboot.actuator.controller;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController implements MeterBinder {

    private Counter counter;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/tq/{city}")
    public Map<String, Object> startInfo(@PathVariable("city") String city) throws Exception {
        //每次调用计数
        this.counter.increment();
        String url = "https://api.tx7.co/api/theweather/?city=" + city;
        return restTemplate.getForObject(url, Map.class);
    }

    /**
     * 定制Metrics信息
     */
    @Override
    public void bindTo(MeterRegistry registry) {
        this.counter = registry.counter("api.call.count");
    }
}