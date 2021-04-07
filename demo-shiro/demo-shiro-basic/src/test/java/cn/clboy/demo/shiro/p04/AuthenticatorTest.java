package cn.clboy.demo.shiro.p04;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

/**
 * 参考:
 * <a>https://www.w3cschool.cn/shiro/xgj31if4.html<a/>
 *
 * SecurityManager接口继承了 Authenticator，另外还有一个 ModularRealmAuthenticator 实现，其委托给多个 Realm 进行验证
 * 验证规则通过 AuthenticationStrategy 接口指定，默认提供的实现：
 * <p>
 * FirstSuccessfulStrategy：只要有一个 Realm 验证成功即可，只返回第一个 Realm 身份验证成功的认证信息，其他的忽略；
 * AtLeastOneSuccessfulStrategy：只要有一个 Realm 验证成功即可，和 FirstSuccessfulStrategy 不同，返回所有 Realm 身份验证成功的认证信息(遇到失败终止)；
 * AllSuccessfulStrategy：所有 Realm 验证成功才算成功，且返回所有 Realm 身份验证成功的认证信息，如果有一个失败就失败了。
 * <p>
 * ModularRealmAuthenticator 默认使用 AtLeastOneSuccessfulStrategy 策略。
 */
public class AuthenticatorTest {


    /**
     * 使用myRealm1,myRealm3
     */
    @Test
    public void testSuccess() {
        init("p4/shiro-p04-success.ini");
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");

        try {
            subject.login(token);
            PrincipalCollection principals = subject.getPrincipals();
            System.out.println(principals.asSet());
        } catch (AuthenticationException e) {
            e.printStackTrace();
            System.err.println("登录失败!");
        }
    }


    /**
     * 使用myRealm1,myRealm2
     */
    @Test
    public void testFaild() {
        init("p4/shiro-p04-faild.ini");
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");

        try {
            subject.login(token);
            PrincipalCollection principals = subject.getPrincipals();
            System.out.println(principals.asSet());
        } catch (AuthenticationException e) {
            e.printStackTrace();
            System.err.println("登录失败!");
        }
    }

    public void init(String configFile) {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:" + configFile);
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
    }
}