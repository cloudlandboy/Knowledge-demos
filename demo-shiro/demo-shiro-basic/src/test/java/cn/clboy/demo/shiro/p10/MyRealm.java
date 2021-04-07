package cn.clboy.demo.shiro.p10;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * Shiro 提供了 CredentialsMatcher 的散列实现 HashedCredentialsMatcher，和之前的 PasswordMatcher 不同的是
 * 它只用于密码验证，且可以提供自己的盐，而不是随机生成盐，且生成密码散列值的算法需要自己写，因为能提供自己的盐。
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
        if (!HashedCredentialsMatcherTest.USERNAME.equals(username)) {
            //UnknownAccountException
            return null;
        }

        //返回正确的用户名/密码信息，后面会调用credentialsMatcher验证token中的密码是否正确
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, HashedCredentialsMatcherTest.PASSWORD, getName());
        //设置盐值
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(HashedCredentialsMatcherTest.SALT));
        return authenticationInfo;
    }
}