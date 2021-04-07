package cn.clboy.demo.shiro.springboot.dto;

import cn.clboy.demo.shiro.springboot.entity.Role;
import lombok.Data;

import java.util.Set;

/**
 * @Author clboy
 * @Date 2021/2/5 下午2:40
 * @Since 1.0.0
 */

@Data
public class RoleInfoDTO extends Role {
    private Set<String> permissions;
}