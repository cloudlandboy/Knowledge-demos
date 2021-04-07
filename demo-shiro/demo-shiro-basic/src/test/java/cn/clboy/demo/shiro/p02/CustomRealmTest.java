package cn.clboy.demo.shiro.p02;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

/**
 * 与helloworld只有配置文件不同
 */
public class CustomRealmTest {

    @Test
    public void startTest() {
        //start 键入用户名密码开始登录
        Subject subject = this.login("zhang", "123456");

        if (subject.isAuthenticated()) {
            System.out.println("做一些事情......");
            this.logout(subject);
        }
    }

    /**
     * 登录
     */
    public Subject login(String username, String password) {

        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:p2/shiro-p02.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
            System.out.println(username + "登录成功！");
        } catch (AuthenticationException e) {
            System.err.println(e);
            System.out.println(username + "登录失败！");
        }
        return subject;
    }

    /**
     * 登出/注销
     */
    public void logout(Subject subject) {

        String username = subject.getPrincipal().toString();
        subject.logout();
        System.out.println(username + "已下线.......");

    }


}