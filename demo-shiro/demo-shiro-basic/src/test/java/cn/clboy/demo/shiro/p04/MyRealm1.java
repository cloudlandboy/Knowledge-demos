package cn.clboy.demo.shiro.p04;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 * 用户名/密码为 zhang/123 时成功，且返回身份/凭据为 zhang/123；
 */
public class MyRealm1 implements Realm {

    /**
     * 返回一个唯一的Realm名字
     */
    @Override
    public String getName() {
        return "myRealm1";
    }

    /**
     * 判断此Realm是否支持此Token
     *
     * @param token
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        //仅支持UsernamePasswordToken类型的Token
        return token instanceof UsernamePasswordToken;
    }

    /**
     * 根据Token获取认证信息
     *
     * @param token
     */
    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //得到用户名
        String username = token.getPrincipal().toString();
        //得到密码
        String password = new String((char[]) token.getCredentials());

        if (!"zhang".equals(username)) {
            //如果用户名错误
            throw new UnknownAccountException();
        }
        if (!"123".equals(password)) {
            //如果密码错误
            throw new IncorrectCredentialsException();
        }

        //如果身份认证验证成功，返回一个AuthenticationInfo实现；
        return new SimpleAuthenticationInfo(username, password, this.getName());
    }
}