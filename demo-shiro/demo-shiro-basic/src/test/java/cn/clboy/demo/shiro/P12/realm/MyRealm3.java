package cn.clboy.demo.shiro.P12.realm;

import cn.clboy.demo.shiro.P12.entity.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;


public class MyRealm3 implements Realm {

    @Override
    public String getName() {
        //realm name 为 “c”
        return "c";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //身份 User类型
        User user = new User("zhang", "123");
        return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
    }
}
