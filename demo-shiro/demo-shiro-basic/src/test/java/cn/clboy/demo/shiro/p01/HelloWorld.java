package cn.clboy.demo.shiro.p01;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;


/**
 * 参考：
 * <a>
 * https://www.w3cschool.cn/shiro/xgj31if4.html
 * </a>
 */
public class HelloWorld {

    @Test
    public void startTest() {
        //start 键入用户名密码开始登录
        Subject subject = this.login("zhang", "123");

        if (subject.isAuthenticated()) {
            System.out.println("做一些事情......");
            this.logout(subject);
        }
    }

    /**
     * 登录
     */
    public Subject login(String username, String password) {

        //1. 通过 new IniSecurityManagerFactory 并指定一个 ini 配置文件来创建一个 SecurityManager 工厂
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:p1/shiro-p01.ini");

        //2. 获取 SecurityManager 并绑定到 SecurityUtils，这是一个全局设置，设置一次即可
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        //3. 通过 SecurityUtils 得到 Subject(主体，代表了当前 “用户”,此时未登录状态)，其会自动绑定到当前线程
        Subject subject = SecurityUtils.getSubject();

        //4. 根据用户名、密码生成token (即用户身份/凭证)
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        //5. 登录（即身份验证）、验证失败会抛出异常
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

        //获取用户身份
        String username = subject.getPrincipal().toString();

        //end. 调用 subject.logout 退出，其会自动委托给 SecurityManager.logout 方法退出
        subject.logout();

        System.out.println(username + "已下线.......");

    }


}