package cn.clboy.demo.shiro.p09;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * Shiro 提供了 PasswordService 及 CredentialsMatcher 用于提供 加密密码 及 验证密码 服务。
 * 参考：<a>https://www.w3cschool.cn/shiro/acnz1ifa.html<a/>
 * <p>
 * 默认的credentialsMatcher：org.apache.shiro.authc.credential.SimpleCredentialsMatcher
 * SimpleCredentialsMatcher:支持对byte []，char []和String类型的凭据进行直接（普通）比较，如果参数与这些类型不匹配，则恢复为简单的Object.equals比较。
 * <p>
 * 将 credentialsMatcher 赋值给 myRealm，myRealm 间接继承了 AuthenticatingRealm
 * 其在调用 getAuthenticationInfo 方法获取到 AuthenticationInfo 信息后，会使用 credentialsMatcher 来验证凭据是否匹配
 * 如果不匹配将抛出 IncorrectCredentialsException 异常
 * 见ini配置文件
 */
public class MyRealm extends AuthorizingRealm {


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = token.getPrincipal().toString();
        //假设从数据库查的
        if (!"zhang".equals(username)) {
            //UnknownAccountException
            return null;
        }
        String pwd = "$shiro1$SHA-512$1$$PJkJr+wlNU1VHa4hWQuybjjVPyFzuNPcPu5MBH56scHri4UQPjvnumE7MbtcnDYhTcnxSkL9ei/bhIVrylxEwg==";

        //返回正确的用户名/密码信息，后面会调用credentialsMatcher验证token中的密码是否正确
        return new SimpleAuthenticationInfo(username, pwd, getName());
    }
}