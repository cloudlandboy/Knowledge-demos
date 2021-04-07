package cn.clboy.demo.shiro.P12.dao;


import cn.clboy.demo.shiro.P12.entity.Permission;

public interface PermissionDao {

    public Permission createPermission(Permission permission);

    public void deletePermission(Long permissionId);

}
