package cn.clboy.demo.shiro.p04;


import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.AbstractAuthenticationStrategy;
import org.apache.shiro.realm.Realm;
import java.util.Collection;

/**
 *  自定义实现时一般继承 org.apache.shiro.authc.pam.AbstractAuthenticationStrategy 即可
 *
 *  因为每个 AuthenticationStrategy 实例都是无状态的，所有每次都通过接口将相应的认证信息传入下一次流程
 */
public class CustomerAuthenticationStrategy extends AbstractAuthenticationStrategy {

    /**
     * 在所有Realm验证之前调用
     */
    @Override
    public AuthenticationInfo beforeAllAttempts(Collection<? extends Realm> realms, AuthenticationToken token) throws AuthenticationException {
        return super.beforeAllAttempts(realms, token);
    }

    /**
     * 在每个Realm之前调用
     */
    @Override
    public AuthenticationInfo beforeAttempt(Realm realm, AuthenticationToken token, AuthenticationInfo aggregate) throws AuthenticationException {
        return super.beforeAttempt(realm, token, aggregate);
    }

    /**
     * 在每个Realm之后调用
     */
    @Override
    public AuthenticationInfo afterAttempt(Realm realm, AuthenticationToken token, AuthenticationInfo singleRealmInfo, AuthenticationInfo aggregateInfo, Throwable t) throws AuthenticationException {
        return super.afterAttempt(realm, token, singleRealmInfo, aggregateInfo, t);
    }

    /**
     * 在所有Realm之后调用
     */
    @Override
    public AuthenticationInfo afterAllAttempts(AuthenticationToken token, AuthenticationInfo aggregate) throws AuthenticationException {
        return super.afterAllAttempts(token, aggregate);
    }
}