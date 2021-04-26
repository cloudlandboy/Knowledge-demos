package cn.clboy.demo.redis.springboot.single.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Serializable {
    private String id;
    private String name;
    private String info;
    private Integer age;

    public Person(String name, String info, Integer age) {
        this.name = name;
        this.info = info;
        this.age = age;
    }
}