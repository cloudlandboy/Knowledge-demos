package cn.clboy.demo.shiro.p11;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    private Ehcache passwordRetryCache;

    public RetryLimitHashedCredentialsMatcher() {
        URL configURL = getClass().getClassLoader().getResource("p11/ehcache.xml");
        CacheManager cacheManager = CacheManager.newInstance(configURL);
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //用户名
        String username = (String) token.getPrincipal();

        Element element = passwordRetryCache.get(username);

        if (element == null) {
            element = new Element(username, new AtomicInteger(0));
            passwordRetryCache.put(element);
        }

        AtomicInteger retryCount = (AtomicInteger) element.getObjectValue();

        if (retryCount.incrementAndGet() > 5) {
            //重试次数大于5次抛出异常
            throw new ExcessiveAttemptsException("操作频繁！！！");
        }

        boolean matches = super.doCredentialsMatch(token, info);

        if (matches) {
            //通过验证，清除缓存中的重试次数
            passwordRetryCache.remove(username);
        }

        return matches;
    }
}