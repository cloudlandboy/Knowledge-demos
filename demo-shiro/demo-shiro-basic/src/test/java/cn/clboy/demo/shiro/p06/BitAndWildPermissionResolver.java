package cn.clboy.demo.shiro.p06;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

/**
 * PermissionResolver 用于解析权限字符串到 Permission 实例
 * BitAndWildPermissionResolver 实现了 PermissionResolver 接口
 * 根据权限字符串是否以 “+” 开头来解析权限字符串为 BitPermission 或 WildcardPermission。
 */
public class BitAndWildPermissionResolver implements PermissionResolver {

    @Override
    public Permission resolvePermission(String permissionString) {
        if (permissionString.startsWith("+")) {
            return new BitPermission(permissionString);
        }
        return new WildcardPermission(permissionString);
    }
}