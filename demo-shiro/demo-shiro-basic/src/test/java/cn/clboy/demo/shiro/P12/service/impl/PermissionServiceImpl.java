package cn.clboy.demo.shiro.P12.service.impl;

import cn.clboy.demo.shiro.P12.dao.PermissionDao;
import cn.clboy.demo.shiro.P12.dao.impl.PermissionDaoImpl;
import cn.clboy.demo.shiro.P12.entity.Permission;
import cn.clboy.demo.shiro.P12.service.PermissionService;

public class PermissionServiceImpl implements PermissionService {

    private PermissionDao permissionDao = new PermissionDaoImpl();

    @Override
    public Permission createPermission(Permission permission) {
        return permissionDao.createPermission(permission);
    }

    @Override
    public void deletePermission(Long permissionId) {
        permissionDao.deletePermission(permissionId);
    }
}
