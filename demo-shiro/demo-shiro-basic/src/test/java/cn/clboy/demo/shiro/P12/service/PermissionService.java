package cn.clboy.demo.shiro.P12.service;

import cn.clboy.demo.shiro.P12.entity.Permission;

public interface PermissionService {
    public Permission createPermission(Permission permission);
    public void deletePermission(Long permissionId);
}
