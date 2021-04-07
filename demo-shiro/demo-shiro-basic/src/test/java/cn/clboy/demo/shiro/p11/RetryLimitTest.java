package cn.clboy.demo.shiro.p11;


import cn.clboy.demo.shiro.utils.BaseTest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Before;
import org.junit.Test;

public class RetryLimitTest extends BaseTest {

    final String PASSWORD = "666888";
    Subject subject;

    @Before
    public void init() {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:p11/shiro-p11.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        subject = SecurityUtils.getSubject();
    }

    @Test
    public void RetryLimitHashedCredentialsMatcherTest() {

        for (int i = 1; i <= 11; i++) {
            UsernamePasswordToken token = new UsernamePasswordToken("sun", i == 5 ? "666888" : "745445");
            try {
                subject.login(token);
                System.out.println("登录成功！");
                subject.logout();
            } catch (AuthenticationException e) {
                System.err.println(i + "\t登录失败！- " + e.getMessage());
            }
        }
    }
}