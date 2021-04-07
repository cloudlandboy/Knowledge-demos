package cn.clboy.demo.shiro.utils;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Test;

public class BaseTest {

    @Test
    public void generateMd5AndSalt2() {
        String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
        Md5Hash md5Hash = new Md5Hash("666888", salt, 2);
        System.out.println("盐值：" + salt);
        System.out.println("密码：" + md5Hash);
    }

    public Subject login(String iniConfigPath, String username, String password) {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:" + iniConfigPath);
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        subject.login(token);
        return subject;
    }

    @After
    public void tearDown() {
        //退出时请解除绑定Subject到线程 否则对下次测试造成影响
        ThreadContext.unbindSubject();
    }

}