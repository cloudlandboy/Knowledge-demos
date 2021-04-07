package cn.clboy.demo.shiro.utils;


import cn.clboy.demo.shiro.P12.entity.User;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * 在创建账户及修改密码时直接把生成密码操作委托给 PasswordHelper
 */
public class PasswordHelper {
    private static String default_algorithmName = "md5";
    private static final int HASHITERATIONS = 2;

    public static User encryptPassword(User user) {
        return encryptPassword(user, default_algorithmName);
    }

    public static User encryptPassword(User user, String algorithmName) {
        //随机盐
        String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
        SimpleHash simpleHash = new SimpleHash(algorithmName, user.getPassword(), salt, HASHITERATIONS);
        //加密后的密码
        String password = simpleHash.toHex();
        user.setSalt(salt);
        user.setPassword(password);
        return user;
    }


}