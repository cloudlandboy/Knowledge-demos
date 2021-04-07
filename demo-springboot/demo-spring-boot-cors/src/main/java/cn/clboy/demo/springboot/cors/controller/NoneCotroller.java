package cn.clboy.demo.springboot.cors.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/none")
public class NoneCotroller {

    @GetMapping("/m1")
    public ResponseEntity<String> m1() {
        return ResponseEntity.ok("finish");
    }

    @PutMapping("/m2")
    public ResponseEntity<String> m2() {
        return ResponseEntity.ok("finish");
    }
}