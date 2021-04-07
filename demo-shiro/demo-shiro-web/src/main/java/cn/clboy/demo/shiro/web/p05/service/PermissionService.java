package cn.clboy.demo.shiro.web.p05.service;


import cn.clboy.demo.shiro.web.p05.entity.Permission;

public interface PermissionService {
    public Permission createPermission(Permission permission);
    public void deletePermission(Long permissionId);
}
