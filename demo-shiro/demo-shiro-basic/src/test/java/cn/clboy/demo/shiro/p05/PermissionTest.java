package cn.clboy.demo.shiro.p05;


import cn.clboy.demo.shiro.utils.ShiroUtil;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import java.util.Arrays;

/**
 * 参考 <a>https://www.w3cschool.cn/shiro/skex1if6.html<a/>
 */
public class PermissionTest {


    /**
     * wang=123,role1
     * role1=user:create,user:update
     * role2=user:create,user:delete
     */
    @Test
    public void testIsPermitted() {
        Subject subject = ShiroUtil.loadFromIni("classpath:p5/shiro-p5.ini");
        UsernamePasswordToken token = new UsernamePasswordToken("wang", "123");
        subject.login(token);

        boolean hasCreateUserP = subject.isPermitted("user:create");
        boolean hasCreateAndUpdateUser = subject.isPermittedAll("user:create", "user:update");
        boolean[] permitted = subject.isPermitted("user:create", "user:update", "user:delete");

        System.out.println(hasCreateUserP);
        System.out.println(hasCreateAndUpdateUser);
        System.out.println(Arrays.toString(permitted));
    }

    @Test
    public void testCheckPermission() {
        Subject subject = ShiroUtil.loadFromIni("classpath:p5/shiro-p5.ini");
        UsernamePasswordToken token = new UsernamePasswordToken("wang", "123");
        subject.login(token);

        subject.checkPermission("user:delete");
        subject.checkPermissions("user:create", "user:update");

        System.out.println("end");
    }
}