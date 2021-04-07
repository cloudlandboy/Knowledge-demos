package cn.clboy.demo.shiro.springboot.configuration;

import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;


@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "shiro")
public class ShiroProperties {

    /**
     * 缓存相关配置
     */
    private cacheProperties cache;

    /**
     * sessionId的cookie配置
     */
    private cookieProperties sessionIdCookie;

    /**
     * 定期的验证会话是否已过期,单位毫秒
     */
    private long sessionValidationSchedulerInterval = DefaultSessionManager.DEFAULT_SESSION_VALIDATION_INTERVAL;

    /**
     * 记住我功能的cookie配置
     */
    private cookieProperties rememberMeCookie;

    /**
     * 对应ini配置中的urls部分
     */
    private Map<String, String> filterChainDefinitionMap;
}