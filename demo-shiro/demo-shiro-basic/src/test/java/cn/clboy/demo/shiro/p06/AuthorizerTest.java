package cn.clboy.demo.shiro.p06;


import cn.clboy.demo.shiro.utils.ShiroUtil;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.Test;


public class AuthorizerTest {

    @Test
    public void testIsPermitted() {
        Subject subject = ShiroUtil.loadFromIni("classpath:p6/shiro-p6.ini");
        subject.login(new UsernamePasswordToken("zhang", "123"));
        boolean i1 = subject.isPermitted("user1:update");
        boolean i2 = subject.isPermitted("user2:update");
        boolean i3 = subject.isPermitted("+user1+2");//新增权限
        boolean i4 = subject.isPermitted("+user1+8");//查看权限
        boolean i5 = subject.isPermitted("+user2+10");//新增及查看
        boolean i6 = subject.isPermitted("+user1+4");//没有删除权限
        boolean i7 = subject.isPermitted("menu:view");//通过MyRolePermissionResolver解析得到的权限

        System.out.println("i1: " + i1);
        System.out.println("i2: " + i2);
        System.out.println("i3: " + i3);
        System.out.println("i4: " + i4);
        System.out.println("i5: " + i5);
        System.out.println("i6: " + i6);
        System.out.println("i7: " + i7);
    }

}