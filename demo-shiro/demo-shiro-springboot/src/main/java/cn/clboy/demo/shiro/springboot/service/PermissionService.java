package cn.clboy.demo.shiro.springboot.service;


import cn.clboy.demo.shiro.springboot.entity.Permission;

import java.util.List;


public interface PermissionService {

    Permission createPermission(Permission permission);

    void deletePermission(Long permissionId);

    List<Permission> getPermissions();

}
