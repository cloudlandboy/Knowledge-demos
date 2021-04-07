package cn.clboy.demo.shiro.p06;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

import java.util.Arrays;
import java.util.Collection;


public class MyRolePermissionResolver implements RolePermissionResolver {

    /**
     * 如果用户拥有 role1，那么就返回一个 “menu:*” 的权限
     */
    @Override
    public Collection<Permission> resolvePermissionsInRole(String roleString) {
        if ("role1".equals(roleString)) {
            Permission permission = new WildcardPermission("menu:*");
            return Arrays.asList(permission);
        }
        return null;
    }
}