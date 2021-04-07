package cn.clboy.demo.shiro.springboot.relam;


import cn.clboy.demo.shiro.springboot.entity.User;
import cn.clboy.demo.shiro.springboot.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        final String username = ((User) principals.getPrimaryPrincipal()).getUsername();
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
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        //设置盐值
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(user.getSalt()));
        return authenticationInfo;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}