package cn.clboy.demo.shiro.web.p05.realm;


import cn.clboy.demo.shiro.web.p05.entity.User;
import cn.clboy.demo.shiro.web.p05.service.UserService;
import cn.clboy.demo.shiro.web.p05.service.impl.UserServiceImpl;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.Set;

public class UserRealm extends AuthorizingRealm {

    private UserService userService = new UserServiceImpl();

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        String username = principals.getPrimaryPrincipal().toString();

        //获取角色
        Set<String> roles = userService.findRoles(username);

        //获取权限
        Set<String> permissions = userService.findPermissions(username);

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permissions);

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = token.getPrincipal().toString();
        User user = userService.findByUsername(username);

        if (user == null) {
            //没找到帐号：返回null等价于 throw new UnknownAccountException();
            throw new UnknownAccountException();
        }

        if (user.getLocked()) {
            //帐号锁定状态
            throw new LockedAccountException();
        }

        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以在此判断或自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, user.getPassword(), getName());
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(user.getSalt()));
        return authenticationInfo;
    }
}