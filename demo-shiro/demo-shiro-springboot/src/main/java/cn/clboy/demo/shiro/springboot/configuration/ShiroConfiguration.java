package cn.clboy.demo.shiro.springboot.configuration;

import cn.clboy.demo.shiro.springboot.credentials.RetryLimitHashedCredentialsMatcher;
import cn.clboy.demo.shiro.springboot.listener.DemoSessionListener;
import cn.clboy.demo.shiro.springboot.mapper.SessionMapper;
import cn.clboy.demo.shiro.springboot.relam.UserRealm;
import cn.clboy.demo.shiro.springboot.service.UserService;
import cn.clboy.demo.shiro.springboot.session.MySessionDAO;
import cn.clboy.demo.shiro.springboot.utils.PasswordHelper;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

/**
 * @Author clboy
 * @Date 2021/2/23 下午3:35
 * @Since 1.0.0
 */
@Configuration
public class ShiroConfiguration {


    @Autowired
    ShiroProperties shiroProperties;

    //@Autowired
    //private freemarker.template.Configuration configuration;

    // 这样设置，访问会报错，见cn.clboy.demo.shiro.springboot.configuration.FreeMarkerShiroTagsConfigurer
    //@PostConstruct
    //public void addFreeMakerTag() {
    //    configuration.setSharedVariable("shiro", new ShiroTags());
    //}

    /**
     * 配置缓存管理器
     *
     * @return
     */
    @Bean
    public CacheManager ehCacheManager() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile(shiroProperties.getCache().getCacheManagerConfigFile());
        return ehCacheManager;
    }

    /**
     * 配置credentialsMatcher(密码匹配器)
     *
     * @param cacheManager
     * @param passwordHelper
     * @return
     */
    @Bean
    public CredentialsMatcher credentialsMatcher(CacheManager cacheManager, PasswordHelper passwordHelper) {
        RetryLimitHashedCredentialsMatcher credentialsMatcher = new RetryLimitHashedCredentialsMatcher();
        credentialsMatcher.setCacheManager(cacheManager);
        credentialsMatcher.setMaxRetryLimit(5);
        credentialsMatcher.setPasswordRetryCacheName(shiroProperties.getCache().getPasswordRetryCacheName());
        credentialsMatcher.setHashAlgorithmName(passwordHelper.getHashAlgorithmName());
        credentialsMatcher.setHashIterations(passwordHelper.getHashIterations());
        return credentialsMatcher;
    }

    /**
     * 配置realm
     *
     * @param userService
     * @param credentialsMatcher
     * @return
     */
    @Bean
    public Realm userRealm(UserService userService, CredentialsMatcher credentialsMatcher) {
        UserRealm userRealm = new UserRealm();
        userRealm.setUserService(userService);
        userRealm.setCredentialsMatcher(credentialsMatcher);
        userRealm.setAuthenticationCacheName(shiroProperties.getCache().getAuthenticationCacheName());
        userRealm.setAuthorizationCacheName(shiroProperties.getCache().getAuthorizationCacheName());
        return userRealm;
    }

    /**
     * 配置sessionDao
     *
     * @param sessionMapper
     * @return
     */
    @Bean
    public SessionDAO sessionDao(SessionMapper sessionMapper) {
        MySessionDAO sessionDAO = new MySessionDAO();
        sessionDAO.setSessionMapper(sessionMapper);
        sessionDAO.setActiveSessionsCacheName(shiroProperties.getCache().getActiveSessionsCacheName());
        return sessionDAO;
    }


    /**
     * session管理器
     *
     * @return
     */
    @Bean
    public SessionManager sessionManager(SessionDAO sessionDAO) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(sessionDAO);

        //设置Cookie名字，默认为JSESSIONID
        sessionManager.getSessionIdCookie().setName(shiroProperties.getSessionIdCookie().getName());
        sessionManager.getSessionIdCookie().setMaxAge(shiroProperties.getSessionIdCookie().getMaxAge());

        //定期的验证会话是否已过期
        ExecutorServiceSessionValidationScheduler sessionValidationScheduler = new ExecutorServiceSessionValidationScheduler();
        sessionValidationScheduler.setInterval(shiroProperties.getSessionValidationSchedulerInterval());
        sessionValidationScheduler.setSessionManager(sessionManager);
        sessionManager.setSessionValidationScheduler(sessionValidationScheduler);

        //session监听器
        ArrayList<SessionListener> listeners = new ArrayList<>();
        listeners.add(new DemoSessionListener());
        sessionManager.setSessionListeners(listeners);

        return sessionManager;
    }


    /**
     * 配置rememberMe管理器
     *
     * @return
     */
    @Bean
    public RememberMeManager rememberMeManager() {
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        rememberMeManager.getCookie().setName(shiroProperties.getRememberMeCookie().getName());
        //过期时间30天，默认一年
        rememberMeManager.getCookie().setMaxAge(shiroProperties.getRememberMeCookie().getMaxAge());
        //设置密钥
        rememberMeManager.setCipherKey("123456789012345".getBytes());
        return rememberMeManager;
    }


    /**
     * 配置securityManager
     *
     * @param userRealm
     * @param sessionManager
     * @param cacheManager
     * @param rememberMeManager
     * @return
     */
    @Bean
    public SecurityManager securityManager(Realm userRealm, SessionManager sessionManager, CacheManager cacheManager, RememberMeManager rememberMeManager) {
        DefaultWebSecurityManager webSecurityManager = new DefaultWebSecurityManager(userRealm);
        webSecurityManager.setSessionManager(sessionManager);
        webSecurityManager.setCacheManager(cacheManager);
        webSecurityManager.setRememberMeManager(rememberMeManager);
        return webSecurityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean factory = new ShiroFilterFactoryBean();
        factory.setSecurityManager(securityManager);
        factory.setLoginUrl("/login");
        factory.setSuccessUrl("/index.html");
        factory.setUnauthorizedUrl("/unauthorized.html");
        factory.setFilterChainDefinitionMap(shiroProperties.getFilterChainDefinitionMap());
        return factory;
    }

    /**
     * 配置shiro注解
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * Spring容器对shiro生命周期的管理 ,基于注解权限拦截需要配置
     * 不配置也可以，具体有什么用暂不了解
     * TODO
     */
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 不配置这个shiro注解不起作用
     * Spring的一个bean , 由Advisor决定对哪些类的方法进行AOP代理
     * TODO
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator autoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        autoProxyCreator.setProxyTargetClass(true);
        return autoProxyCreator;
    }
}