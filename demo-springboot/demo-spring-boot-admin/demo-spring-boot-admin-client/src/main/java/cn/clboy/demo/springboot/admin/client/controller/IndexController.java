package cn.clboy.demo.springboot.admin.client.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String actuator(){
        return "redirect:/actuator";
    }
}