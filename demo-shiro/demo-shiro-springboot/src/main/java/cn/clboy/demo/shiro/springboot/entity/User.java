package cn.clboy.demo.shiro.springboot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;


@Data
@NoArgsConstructor
public class User implements Serializable {
    private Long id;

    @NotBlank(message = "用户名不能为空")
    @Length(min = 3, max = 8, message = "用户名仅支持3-8位字符")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9@.$]{3,9}$", message = "密码仅支持3-9位数字,英文及@.$符号")
    private String password;
    private String salt;
    private Boolean locked = Boolean.FALSE;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
