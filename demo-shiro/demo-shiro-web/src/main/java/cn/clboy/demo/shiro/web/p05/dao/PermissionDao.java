package cn.clboy.demo.shiro.web.p05.dao;


import cn.clboy.demo.shiro.web.p05.entity.Permission;

public interface PermissionDao {

    public Permission createPermission(Permission permission);

    public void deletePermission(Long permissionId);

}
