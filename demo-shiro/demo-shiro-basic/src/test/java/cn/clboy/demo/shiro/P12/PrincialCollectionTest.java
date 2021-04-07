package cn.clboy.demo.shiro.P12;


import cn.clboy.demo.shiro.utils.BaseTest;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * 我们可以直接调用 subject.getPrincipal 获取 PrimaryPrincipal（即所谓的第一个）；
 * 或者通过 getPrincipals 获取 PrincipalCollection；然后通过其 getPrimaryPrincipal 获取 PrimaryPrincipal。
 */
public class PrincialCollectionTest extends BaseTest {

    @Test
    public void multirealmTest() {
        String username = "zhang";
        String password = "123";
        Subject subject = login("p12/shiro-multirealm.ini", username, password);

        Object primaryPrincipal1 = subject.getPrincipal();
        PrincipalCollection princialCollection = subject.getPrincipals();
        Object primaryPrincipal2 = princialCollection.getPrimaryPrincipal();

        System.out.println(primaryPrincipal1);
        System.out.println(princialCollection);
        System.out.println(primaryPrincipal2);
    }
}