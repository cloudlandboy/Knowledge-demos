package cn.clboy.demo.shiro.p09;


import cn.clboy.demo.shiro.utils.ShiroUtil;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class PasswordTest {

    /**
     * passwordService 使用 DefaultPasswordService，如果有必要也可以自定义
     * 实际使用时需要在 Service 层使用 passwordService 加密密码并存到数据库。
     */
    DefaultPasswordService passwordService = new DefaultPasswordService();

    {
        //定义散列密码使用的 HashService，默认使用 DefaultHashService（并且设置默认 SHA-256 算法）
        //这里重新设置，因为DefaultPasswordService中将DefaultHashService的generatePublicSalt设为了true，会每次随机生成盐
        passwordService.setHashService(new DefaultHashService());

        //hashFormat 用于对散列出的值进行格式化，默认使用 Shiro1CryptFormat，另外提供了 Base64Format 和 HexFormat
        // 对于有 salt 的密码请自定义实现 ParsableHashFormat 然后把 salt 格式化到散列值中；
        //passwordService.setHashFormat(new Shiro1CryptFormat());

        //hashFormatFactory 用于根据散列值得到散列的密码和 salt；因为如果使用如 SHA 算法，那么会生成一个 salt
        // 此 salt 需要保存到散列后的值中以便之后与传入的密码比较时使用；默认使用 DefaultHashFormatFactory；
        //passwordService.setHashFormatFactory(new DefaultHashFormatFactory());


    }

    @Test
    public void generateSecretPwd() {
        System.out.println(passwordService.encryptPassword("123"));
    }

    @Test
    public void testPasswordServiceWithMyRealm() {

        Subject subject = ShiroUtil.loadFromIni("classpath:p9/shiro-p9.ini");
        String username = "zhang";
        String password = "123";
        // 这是用户输入的
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        subject.login(token);

        System.out.println("成功登录！");
    }
}