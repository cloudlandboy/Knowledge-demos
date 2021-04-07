package cn.clboy.demo.shiro.spring.utils;


import cn.clboy.demo.shiro.spring.entity.User;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * 在创建账户及修改密码时直接把生成密码操作委托给 PasswordHelper
 */
public class PasswordHelper {

    private final String DEFAULT_HASHALGORITHMNAME = "md5";
    private final int DEFAULT_HASHITERATIONS = 2;

    private String hashAlgorithmName = DEFAULT_HASHALGORITHMNAME;

    private int hashIterations = DEFAULT_HASHITERATIONS;

    public User encryptPassword(User user) {
        return encryptPassword(user, hashAlgorithmName);
    }

    public User encryptPassword(User user, String algorithmName) {
        //随机盐
        String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
        SimpleHash simpleHash = new SimpleHash(algorithmName, user.getPassword(), salt, hashIterations);
        //加密后的密码
        String password = simpleHash.toHex();
        user.setSalt(salt);
        user.setPassword(password);
        return user;
    }

    public String getHashAlgorithmName() {
        return hashAlgorithmName;
    }

    public void setHashAlgorithmName(String hashAlgorithmName) {
        this.hashAlgorithmName = hashAlgorithmName;
    }

    public int getHashIterations() {
        return hashIterations;
    }

    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }
}