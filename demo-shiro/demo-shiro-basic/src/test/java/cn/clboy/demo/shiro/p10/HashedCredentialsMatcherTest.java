package cn.clboy.demo.shiro.p10;


import cn.clboy.demo.shiro.utils.BaseTest;
import org.junit.Test;

public class HashedCredentialsMatcherTest extends BaseTest {

    static final String USERNAME = "zhang";
    static final String PASSWORD = "962869204262ef940eeb9a5f1321300e";
    static final String SALT = "2e7718ea76a25b2509a0ea3ebaaa579f";

    @Test
    public void generateMd5AndSaltPwd() {
        //String password = "68966";
        //// SecureRandomNumberGenerator 用于生成一个随机数
        //String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
        //System.out.println("盐值：" + salt);
        //Md5Hash md5Pwd = new Md5Hash(password, salt, 2);
        //System.out.println("密文：" + md5Pwd);
    }

    @Test
    public void hashedCredentialsMatcherTest() {
        login("p10/shiro-p9.ini", USERNAME, "2468");
        System.out.println("登录成功！");
    }
}