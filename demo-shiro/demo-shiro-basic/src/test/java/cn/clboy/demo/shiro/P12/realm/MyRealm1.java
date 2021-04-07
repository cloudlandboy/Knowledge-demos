package cn.clboy.demo.shiro.P12.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;


public class MyRealm1 implements Realm {

    @Override
    public String getName() {
        //realm name 为 “a”
        return "a";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //身份 字符串类型
        String principal = "zhang";
        //凭据
        String password = "132";
        return new SimpleAuthenticationInfo(principal, password, getName());

    }
}
