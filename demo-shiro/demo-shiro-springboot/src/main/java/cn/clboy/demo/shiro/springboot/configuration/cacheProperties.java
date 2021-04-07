package cn.clboy.demo.shiro.springboot.configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class cacheProperties {
    /**
     * 缓存配置文件
     */
    private String cacheManagerConfigFile;

    /**
     * 密码重试记录缓存名称
     */
    private String passwordRetryCacheName;

    /**
     * 认证的缓存名称
     */
    private String authenticationCacheName;

    /**
     * 授权的缓存名称
     */
    private String authorizationCacheName;

    /**
     * session的缓存名称
     */
    private String activeSessionsCacheName;
}