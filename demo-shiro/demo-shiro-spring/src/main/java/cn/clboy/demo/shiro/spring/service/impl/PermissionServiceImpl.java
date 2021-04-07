package cn.clboy.demo.shiro.spring.service.impl;

import cn.clboy.demo.shiro.spring.entity.Permission;
import cn.clboy.demo.shiro.spring.mapper.PermissionMapper;
import cn.clboy.demo.shiro.spring.service.PermissionService;

import java.util.List;


public class PermissionServiceImpl implements PermissionService {

    private PermissionMapper permissionMapper;

    public Permission createPermission(Permission permission) {
        return permissionMapper.createPermission(permission);
    }

    public void deletePermission(Long permissionId) {
        permissionMapper.deletePermission(permissionId);
    }

    @Override
    public List<Permission> getPermissions() {
        return permissionMapper.getPermissions();
    }

    public void setPermissionMapper(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }
}
