package cn.clboy.demo.springboot.cors.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.lang.reflect.Field;
import java.util.Map;


@Controller
@RequestMapping("/filter")
public class CorsFilterController {

    @Autowired(required = false)
    CorsFilter corsFilter;

    @GetMapping("/m1")
    public ResponseEntity<String> m1() {
        return ResponseEntity.ok("finish");
    }

    @PutMapping("/m2")
    public ResponseEntity<String> m2() {
        return ResponseEntity.ok("finish");
    }


    @PostMapping("/change_allowed_credentials")
    public ResponseEntity<Boolean> change(Boolean allowed) throws NoSuchFieldException, IllegalAccessException {
        Field field = corsFilter.getClass().getDeclaredField("configSource");
        field.setAccessible(true);

        UrlBasedCorsConfigurationSource configSource = (UrlBasedCorsConfigurationSource) field.get(corsFilter);
        Map<String, CorsConfiguration> corsConfigurations = configSource.getCorsConfigurations();
        CorsConfiguration corsConfiguration = corsConfigurations.get("/filter/**");
        corsConfiguration.setAllowCredentials(allowed);
        return ResponseEntity.ok(true);
    }
}