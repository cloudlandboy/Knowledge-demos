package cn.clboy.demo.shiro.p02;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 * @description: 自定义 Realm 实现
 * <p>
 * Realm：域
 * Shiro 从 Realm 获取安全数据（如用户、角色、权限）
 * 就是说 SecurityManager 要验证用户身份，那么它需要从 Realm 获取相应的用户进行比较以确定用户身份是否合法
 * 也需要从 Realm 得到用户相应的角色 / 权限进行验证用户是否能进行操作
 * 可以把 Realm 看成 DataSource，即安全数据源
 * 如我们之前的 ini 配置方式将使用 org.apache.shiro.realm.text.IniRealm
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
        if (!"123456".equals(password)) {
            //如果密码错误
            throw new IncorrectCredentialsException();
        }

        //如果身份认证验证成功，返回一个AuthenticationInfo实现；
        return new SimpleAuthenticationInfo(username, password, this.getName());
    }
}