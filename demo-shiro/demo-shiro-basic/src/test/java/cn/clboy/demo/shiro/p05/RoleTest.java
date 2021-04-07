package cn.clboy.demo.shiro.p05;


import cn.clboy.demo.shiro.utils.ShiroUtil;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import java.util.Arrays;

/**
 * 参考: <a>https://www.w3cschool.cn/shiro/skex1if6.html</a>
 * 在授权中需了解的几个关键对象：
 * 主体（Subject）: 主体，即访问应用的用户，在 Shiro 中使用 Subject 代表该用户。用户只有授权后才允许访问相应的资源
 * 资源（Resource）: 在应用中用户可以访问的URL
 * 权限（Permission）: 安全策略中的原子授权单位，通过权限我们可以表示在应用中用户有没有操作某个资源的权力。即权限表示在应用中用户能不能访问某个资源
 * 角色（Role): 角色代表了操作集合，可以理解为权限的集合，一般情况下我们会赋予用户角色而不是权限，即这样用户可以拥有一组权限
 */
public class RoleTest {


    /**
     * zhang=123,role1,role2
     */
    @Test
    public void testHasRole() {
        Subject subject = ShiroUtil.loadFromIni("classpath:p5/shiro-p5.ini");
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
        subject.login(token);

        boolean hasRole1 = subject.hasRole("role1");
        boolean hasRole1AndRole2 = subject.hasAllRoles(Arrays.asList("role1", "role2"));
        boolean[] hasRoles = subject.hasRoles(Arrays.asList("role1", "role2", "role3"));

        System.out.println(hasRole1);
        System.out.println(hasRole1AndRole2);
        System.out.println(Arrays.toString(hasRoles));
    }

    /**
     * zhang=123,role1,role2
     * checkRole/checkRoles在判断为假的情况下会抛出 UnauthorizedException 异常。
     */
    @Test
    public void testCheckRole() {
        Subject subject = ShiroUtil.loadFromIni("classpath:p5/shiro-p5.ini");
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
        subject.login(token);

        subject.checkRole("role1");
        subject.checkRoles("role1", "role2");
        subject.checkRoles("role1", "role2", "role3");

    }


}