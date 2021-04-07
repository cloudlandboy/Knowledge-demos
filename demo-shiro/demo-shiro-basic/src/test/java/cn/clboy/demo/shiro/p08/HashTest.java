package cn.clboy.demo.shiro.p08;


import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.hash.*;
import org.junit.Test;

import java.security.Key;
import java.util.Arrays;

/**
 * 散列算法一般用于生成数据的摘要信息，是一种不可逆的算法，一般适合存储密码之类的数据，常见的散列算法如 MD5、SHA 等
 * 一般进行散列时最好提供一个 salt（盐）
 * 如果直接对密码进行散列相对来说破解更容易，此时我们可以加一些只有系统知道的干扰数据；如:
 * 用户名和 ID（即盐）,这样散列的对象是 “密码 + 用户名 +ID”，这样生成的散列值相对来说更难破解
 * <p>
 * 其他参考 <a>https://github.com/zhangkaitao/shiro-example/blob/master/shiro-example-chapter5/src/test/java/com/github/zhangkaitao/shiro/chapter5/hash/CodecAndCryptoTest.java<a/>
 */
public class HashTest {

    @Test
    public void md5Test() {
        String pwd = "123465";
        String salt = "b-1997";

        String md5 = new Md5Hash(pwd, salt).toString();
        //=
        String md52 = new Md5Hash(salt + pwd).toString();
        System.out.println(md5);
        System.out.println(md52);

    }

    @Test
    public void testSha1() {
        String str = "hello";
        String salt = "123";
        String sha1 = new Sha1Hash(str, salt).toString();
        System.out.println(sha1);

    }

    @Test
    public void testSha256() {
        String str = "hello";
        String salt = "123";
        String sha1 = new Sha256Hash(str, salt).toString();
        System.out.println(sha1);

    }

    @Test
    public void testSha384() {
        String str = "hello";
        String salt = "123";
        String sha1 = new Sha384Hash(str, salt).toString();
        System.out.println(sha1);

    }

    @Test
    public void testSha512() {
        String str = "hello";
        String salt = "123";
        String sha1 = new Sha512Hash(str, salt).toString();
        System.out.println(sha1);

    }


    /**
     * 通过调用 SimpleHash 时指定散列算法，其内部使用了 Java 的 MessageDigest 实现。
     */
    @Test
    public void testSimpleHash() {
        String str = "hello";
        String salt = "123";
        //MessageDigest
        String simpleHash = new SimpleHash("SHA-1", str, salt).toString();
        System.out.println(simpleHash);

    }

    /**
     * Shiro 还提供对称式加密 / 解密算法的支持，如 AES、Blowfish 等
     */
    @Test
    public void testAesCipherService() {
        AesCipherService aesCipherService = new AesCipherService();
        aesCipherService.setKeySize(128);//设置key长度

        //生成key
        Key key = aesCipherService.generateNewKey();
        String pwd = "hello";

        //加密
        String encrptText = aesCipherService.encrypt(pwd.getBytes(), key.getEncoded()).toHex();
        //解密
        String decryptPwd = new String(aesCipherService.decrypt(Hex.decode(encrptText), key.getEncoded()).getBytes());
        System.out.println(pwd);
        System.out.println(encrptText);
        System.out.println(decryptPwd);
    }
}