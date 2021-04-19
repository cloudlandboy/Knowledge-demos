package cn.clboy.demo.rabbitmq.springboot.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyCode implements Serializable {

    private String code;

    private Date createTime;
}