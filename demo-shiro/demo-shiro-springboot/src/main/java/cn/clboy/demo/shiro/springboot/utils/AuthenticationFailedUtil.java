package cn.clboy.demo.shiro.springboot.utils;


import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationFailedUtil {

    private static final Map<String, String> MSGMAP = new HashMap();

    static {
        MSGMAP.put(UnknownAccountException.class.getName(), "用户名/密码错误");
        MSGMAP.put(IncorrectCredentialsException.class.getName(), "用户名/密码错误");
        MSGMAP.put(ExcessiveAttemptsException.class.getName(), "密码尝试次数过多,请10分钟过后重试!");
        MSGMAP.put(LockedAccountException.class.getName(), " 账户已被禁用!");
    }

    public static String getMsg(String errorClassName) {
        String msg = MSGMAP.get(errorClassName);
        return msg == null ? "未知错误：" + errorClassName : msg;
    }
}