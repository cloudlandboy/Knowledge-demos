package cn.clboy.demo.shiro.spring.service;


import cn.clboy.demo.shiro.spring.entity.Permission;

import java.util.List;


public interface PermissionService {

    Permission createPermission(Permission permission);

    void deletePermission(Long permissionId);

    List<Permission> getPermissions();

}
