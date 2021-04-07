package cn.clboy.demo.ssh.entity;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;


@Data
@Entity
@Table(name = "t_user")
@Validated
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "用户名不可为空")
    @Column(name = "username")
    private String username;

    @NotNull(message = "密码不可为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{3,16}$", message = "密码需为3~16位英文或数字")
    @Column(name = "password")
    private String password;

}