package cn.clboy.demo.shiro.spring.mapper;

import cn.clboy.demo.shiro.spring.entity.Permission;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author clboy
 * @Date 2021/2/1 上午10:31
 * @Since 1.0.0
 */

public interface PermissionMapper {

    Permission createPermission(Permission permission);

    void deletePermission(Long permissionId);

    @Select("SELECT * FROM sys_permissions")
    List<Permission> getPermissions();
}