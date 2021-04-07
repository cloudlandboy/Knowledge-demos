package cn.clboy.demo.shiro.web.p05.credentials;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author clboy
 * @Description 密码重试限制CredentialsMatcher
 **/
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    private final Logger LOGGER = LoggerFactory.getLogger(RetryLimitHashedCredentialsMatcher.class);

    private final int DEFAULT_MAXRETRYLIMIT = 5;
    private final String DEFAULT_PASSWORDRETRYCACHENAME = "passwordRetryCache";

    private CacheManager cacheManager;
    private Cache<String, AtomicInteger> passwordRetryCache;

    private int maxRetryLimit = DEFAULT_MAXRETRYLIMIT;
    private String passwordRetryCacheName = DEFAULT_PASSWORDRETRYCACHENAME;


    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String) token.getPrincipal();
        AtomicInteger retryCount = passwordRetryCache.get(username);
        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(username, retryCount);
        }
        if (retryCount.incrementAndGet() > maxRetryLimit) {
            throw new ExcessiveAttemptsException();
        }
        boolean matches = super.doCredentialsMatch(token, info);
        if (matches) {
            passwordRetryCache.remove(username);
        }
        return matches;
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
        assignCache();
    }

    public String getPasswordRetryCacheName() {
        return passwordRetryCacheName;
    }

    public void setPasswordRetryCacheName(String passwordRetryCacheName) {
        this.passwordRetryCacheName = passwordRetryCacheName;
        assignCache();
    }

    public int getMaxRetryLimit() {
        return maxRetryLimit;
    }

    public void setMaxRetryLimit(int maxRetryLimit) {
        this.maxRetryLimit = maxRetryLimit;
    }

    private void assignCache() {
        this.passwordRetryCache = this.cacheManager.getCache(this.passwordRetryCacheName);
    }
}
