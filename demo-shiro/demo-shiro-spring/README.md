# spring整合shiro

> 持久层使用mybatis框架，全注解方式
>
> 模板引擎freemarker



## 运行

```shell
mvn tomcat7:run-war
```

1. 访问 http://localhost:8080/shiro-spring

2. 点击用户列表（未登录用户可以查看用户列表）:writing_hand: ​`/admin/user/users=anon`

3. 因为配置了shiro权限标签，所以操作选项中看不到`编辑`和`删除`

   ```html
   <@shiro.hasPermission name="user:update">
       <el-button type="text" size="small">编辑</el-button>
       <el-button type="text" size="small">删除</el-button>
   </@shiro.hasPermission>
   ```

4. 角色列表和权限列表均需要拥有管理员`admin`的角色才能访问 :writing_hand: `/admin/**=user,roles[admin]`

5. 拥有管理员的账户密码：

   zhang---123456

6. 

## 主要相关依赖


```xml
        <!-- shiro和spring整合（传递依赖shiro-core和shiro-web）-->
        <dependency>
            <groupId>org.apache.shiro</groupId>
            <artifactId>shiro-spring</artifactId>
            <version>1.7.0</version>
        </dependency>

        <!-- shiro整合ehcache -->
        <dependency>
            <groupId>org.apache.shiro</groupId>
            <artifactId>shiro-ehcache</artifactId>
            <version>1.7.0</version>
        </dependency>

        <!-- spring-web-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.2.8.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context-support</artifactId>
            <version>5.2.8.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>5.2.8.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aspects</artifactId>
            <version>5.2.8.RELEASE</version>
        </dependency>
```



## 主要相关配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:util="http://www.springframework.org/schema/util"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/util https://www.springframework.org/schema/util/spring-util.xsd http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd">

    <!-- 一些配置 -->
    <util:properties id="hashProperties">
        <!-- 使用的hash算法 -->
        <prop key="hashAlgorithmName">MD5</prop>
        <!-- hash迭代次数(就是对产生的md5再次加密) -->
        <prop key="hashIterations">2</prop>
    </util:properties>

    <!-- 配置缓存管理器 -->
    <bean name="cacheManager" class="org.apache.shiro.cache.ehcache.EhCacheManager">
        <property name="cacheManagerConfigFile" value="classpath:shiro-ehcache.xml"/>
    </bean>

    <!-- 配置credentialsMatcher(密码匹配器) -->
    <bean name="credentialsMatcher" class="RetryLimitHashedCredentialsMatcher">
        <property name="cacheManager" ref="cacheManager"/>
        <!-- 最大重试次数 -->
        <property name="maxRetryLimit" value="5"/>
        <property name="hashAlgorithmName" value="#{hashProperties.hashAlgorithmName}"/>
        <property name="hashIterations" value="#{hashProperties.hashIterations}"/>
        <property name="passwordRetryCacheName" value="passwordRetryCache"/>
    </bean>

    <!-- 配置realm -->
    <bean name="userRealm" class="UserRealm">
        <property name="userService" ref="userService"/>
        <property name="credentialsMatcher" ref="credentialsMatcher"/>
        <property name="cachingEnabled" value="true"/>
        <property name="authenticationCachingEnabled" value="true"/>
        <property name="authenticationCacheName" value="authenticationCache"/>
        <property name="authorizationCachingEnabled" value="true"/>
        <property name="authorizationCacheName" value="authorizationCache"/>
    </bean>

    <!-- sessionDao -->
    <bean name="sessionDAO" class="MySessionDAO">
        <property name="sessionMapper" ref="sessionMapper"/>
        <property name="activeSessionsCacheName" value="shiro-activeSessionCache"/>
    </bean>

    <!-- 定期的验证会话是否已过期 -->
    <bean name="sessionValidationScheduler"
          class="org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler">
        <property name="interval" value="#{1000*30*5}"/>
        <property name="sessionManager" ref="sessionManager"/>
    </bean>

    <!-- session监听器 -->
    <bean name="sessionListener" class="DemoSessionListener"/>

    <!-- session管理器-->
    <bean name="sessionManager" class="org.apache.shiro.web.session.mgt.DefaultWebSessionManager">
        <property name="sessionDAO" ref="sessionDAO"/>
        <property name="globalSessionTimeout" value="#{1000*60*10}"/>
        <!-- 设置Cookie名字，默认为JSESSIONID -->
        <property name="sessionIdCookie.name" value="sid"/>
        <!-- 表示浏览器关闭时失效此 Cookie 默认也是—1，测试remember-->
        <property name="sessionIdCookie.maxAge" value="-1"/>
        <property name="sessionValidationSchedulerEnabled" value="true"/>
        <property name="sessionValidationScheduler" ref="sessionValidationScheduler"/>
        <property name="sessionListeners" ref="sessionListener"/>
    </bean>

    <!-- rememberMe管理器 -->
    <bean name="rememberMeManager" class="org.apache.shiro.web.mgt.CookieRememberMeManager">
        <!-- 默认SimpleCookie,cookie名为rememberMe-->
        <property name="cookie.name" value="token"/>
        <!-- 过期时间30天，默认一年 -->
        <property name="cookie.maxAge" value="#{3600*24*30}"/>
        <!-- 设置密钥，密钥长度支持（16/24/32）-->
        <property name="cipherKey" value="#{T(org.apache.shiro.codec.Base64).decode('7jTQxw//pkbYKhiLzrhOvQ==')}"/>
    </bean>

    <!-- name如果和默认的filtername一致就会替换默认的-->
    <bean name="logout" class="org.apache.shiro.web.filter.authc.LogoutFilter">
        <property name="redirectUrl" value="/"/>
    </bean>

    <!-- 配置securityManager -->
    <bean name="securityManager" class="org.apache.shiro.web.mgt.DefaultWebSecurityManager">
        <property name="cacheManager" ref="cacheManager"/>
        <property name="realm" ref="userRealm"/>
        <property name="sessionManager" ref="sessionManager"/>
        <property name="rememberMeManager" ref="rememberMeManager"/>
    </bean>

    <!-- 配置shiroFilter -->
    <bean name="shiroFilter" class="org.apache.shiro.spring.web.ShiroFilterFactoryBean">
        <property name="securityManager" ref="securityManager"/>
        <property name="loginUrl" value="/login"/>
        <property name="successUrl" value="/index.html"/>
        <property name="unauthorizedUrl" value="/unauthorized.html"/>
        <!-- 对应ini配置中的urls部分-->
        <property name="filterChainDefinitions">
            <value>
                /login=authc
                /logout=logout
                /admin/user/users=anon
                /admin/**=user,roles[admin]
            </value>
        </property>
    </bean>
</beans>
```



## Ehcache缓存配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<ehcache name="es">

    <diskStore path="java.io.tmpdir/shiro-ehcache"/>

    <!-- 认证的缓存 -->
    <cache name="authenticationCache"
           maxEntriesLocalHeap="2000"
           eternal="false"
           timeToIdleSeconds="3600"
           timeToLiveSeconds="0"
           overflowToDisk="false"
           statistics="true">
    </cache>

    <!-- 授权的缓存 -->
    <cache name="authorizationCache"
           maxEntriesLocalHeap="2000"
           eternal="false"
           timeToIdleSeconds="3600"
           timeToLiveSeconds="0"
           overflowToDisk="false"
           statistics="true">
    </cache>

    <!-- session缓存 -->
    <cache name="shiro-activeSessionCache"
           maxEntriesLocalHeap="10000"
           overflowToDisk="false"
           eternal="false"
           diskPersistent="false"
           timeToLiveSeconds="0"
           timeToIdleSeconds="0"
           statistics="true"/>

    <!-- 登录记录缓存 锁定10分钟 -->
    <cache name="passwordRetryCache"
           maxEntriesLocalHeap="2000"
           eternal="false"
           timeToIdleSeconds="3600"
           timeToLiveSeconds="0"
           overflowToDisk="false"
           statistics="true">
    </cache>
</ehcache>
```





## 开启shiro注解的配置

> 这个要配置在springMvc的配置文件 中（默认DispatcherServlet.xml）

```xml
    <!-- 开启shiro注解 要在web配置文件中配置，作用于controller-->
    <!-- 指定使用cglib代理 -->
    <aop:config proxy-target-class="true"/>

    <!-- 配置shiro框架提供的切面类，用于创建代理对象 -->
    <bean class="org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor">
        <property name="securityManager" ref="securityManager"/>
    </bean>
```

```java
    @GetMapping("/loginUserInfo")
    @RequiresUser
    public String loginUserInfo(Model model) 
```



## Freemarker中使用shiro的JSP标签

> :point_right: 使用`tomcat7:run`的方式Freemarker会读取不到jar包里的jsp标签文件，所以要用`tomcat7:run-war`的方式运行

```html
<#-- 引用shiro的jsp标签 -->
<#assign shiro=JspTaglibs["http://shiro.apache.org/tags"]>
    
<@shiro.hasPermission name="user:update">
    <el-button type="text" size="small">编辑</el-button>
    <el-button type="text" size="small">删除</el-button>
</@shiro.hasPermission>
```
