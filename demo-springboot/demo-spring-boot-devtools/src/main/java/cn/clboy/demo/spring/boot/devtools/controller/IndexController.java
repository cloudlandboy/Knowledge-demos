package cn.clboy.demo.spring.boot.devtools.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class IndexController {

    @Value("${spring.application.name}")
    private String appName;

    @GetMapping("/index")
    public String index(Model model) {
        //修改生成数的范围，测试是否要重启
        List<Integer> data = IntStream.range(1, 9).boxed().collect(Collectors.toList());
        model.addAttribute("data",data);
        model.addAttribute("appName",appName);
        return "index";
    }
}