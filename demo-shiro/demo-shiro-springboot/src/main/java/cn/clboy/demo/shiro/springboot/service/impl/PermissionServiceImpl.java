package cn.clboy.demo.shiro.springboot.service.impl;


import cn.clboy.demo.shiro.springboot.entity.Permission;
import cn.clboy.demo.shiro.springboot.mapper.PermissionMapper;
import cn.clboy.demo.shiro.springboot.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
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
