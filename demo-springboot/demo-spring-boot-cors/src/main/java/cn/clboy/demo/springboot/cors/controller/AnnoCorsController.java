package cn.clboy.demo.springboot.cors.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 使用注解的方式
 * CrossOrigin注解写在类上，作用于类中所有方法
 */
@Controller
@RequestMapping("/anno")
public class AnnoCorsController {


    /**
     * 默认Origin为 *
     * 默认受支持的方法与控制器方法所映射的方法相同
     */
    @CrossOrigin
    @GetMapping("/m1")
    public ResponseEntity<String> m1() {
        return ResponseEntity.ok("finish");
    }

    @CrossOrigin
    @PutMapping("/m2")
    public ResponseEntity<String> m2() {
        return ResponseEntity.ok("finish");
    }
}