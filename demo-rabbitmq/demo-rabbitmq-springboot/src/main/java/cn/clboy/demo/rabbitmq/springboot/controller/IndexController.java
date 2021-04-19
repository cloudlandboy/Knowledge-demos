package cn.clboy.demo.rabbitmq.springboot.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String toApi() {
        return "redirect:/swagger-ui.html";
    }
}