package cn.clboy.demo.shiro.springboot.configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class cookieProperties {
    private String name;
    private int maxAge;
    private String domain;
    private String path;
}