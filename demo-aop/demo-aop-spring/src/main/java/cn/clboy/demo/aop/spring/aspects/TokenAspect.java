package cn.clboy.demo.aop.spring.aspects;


import cn.clboy.demo.aop.spring.annos.RequireToken;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Map;

@Aspect
@Component
public class TokenAspect {


    /**
     * @param joinPoint    必须放在参数第一位
     * @param requireToken
     * @return
     * @throws Throwable
     */
    @Before(value = "@annotation(requireToken)")
    public void checkToken(JoinPoint joinPoint, RequireToken requireToken) throws Throwable {
        String tokenName = requireToken.value();
        if ("".equals(tokenName)) {
            tokenName = requireToken.tokenName();
        }

        Map<String, String> param = (Map<String, String>) joinPoint.getArgs()[0];

        if (!param.containsKey(tokenName)) {
            throw new RuntimeException("没有权限，禁止操作！");
        }

    }
}