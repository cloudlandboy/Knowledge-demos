package cn.clboy.demo.shiro.web.p02.filter;


import org.apache.shiro.web.env.IniWebEnvironment;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;

/**
 * Shiro 对 Servlet 容器的 FilterChain 进行了代理
 * ProxiedFilterChain 对 Servlet 容器的 FilterChain 进行了代理；
 * Shiro 的 ProxiedFilterChain 执行流程：
 * - 1、先执行 Shiro 自己的 Filter 链；
 * - 2、再执行 Servlet 容器的 Filter 链（即原始的 Filter）
 * 而 ProxiedFilterChain 是通过 FilterChainResolver 根据配置文件中[urls]部分是否与请求的 URL 是否匹配解析得到的。
 * Shiro 内部提供了一个路径匹配的 FilterChainResolver 实现类：PathMatchingFilterChainResolver
 * 而 PathMatchingFilterChainResolver 内部通过 FilterChainManager 维护着拦截器链
 * 比如 DefaultFilterChainManager 实现维护着 url 模式与拦截器链的关系
 * 因此我们可以通过 FilterChainManager 进行动态动态增加 url 模式与拦截器链的关系。
 * DefaultFilterChainManager 会默认添加 org.apache.shiro.web.filter.mgt.DefaultFilter 中声明的拦截器
 * 注册自定义拦截器：
 * - IniSecurityManagerFactory/WebIniSecurityManagerFactory 在启动时会自动扫描 ini 配置文件中的 [filters]/[main] 部分
 * 并注册这些拦截器到 DefaultFilterChainManager且创建相应的 url 模式与其拦截器关系链
 * <p>
 * 如果想自定义 FilterChainResolver，可以通过实现 WebEnvironment 接口完成：
 * WebEnvironment
 * -MutableWebEnvironment
 * -- DefaultWebEnvironment
 * ---  ResourceBasedWebEnvironment
 * ----   IniWebEnvironment
 */
public class MyIniWebEnvironment extends IniWebEnvironment {

    @Override
    protected FilterChainResolver createFilterChainResolver() {
        //1、创建 FilterChainResolver
        PathMatchingFilterChainResolver filterChainResolver = new PathMatchingFilterChainResolver();

        //2、创建 FilterChainManager
        DefaultFilterChainManager filterChainManager = new DefaultFilterChainManager();

        //3、注册Filter
        //3.1 DefaultFilter 中声明的Filter (实例化DefaultFilterChainManager时就会添加)
        //for (DefaultFilter filter : DefaultFilter.values()) {
        //    filterChainManager.addFilter(
        //            filter.name(), (Filter) ClassUtils.newInstance(filter.getFilterClass()));
        //}

        //3.2 注册自定义filter
        filterChainManager.addFilter("myFilter1", new MyOncePerRequestFilter());
        filterChainManager.addFilter("myAdvFilter1", new MyAdviceFilter1());
        filterChainManager.addFilter("myAdvFilter2", new MyAdviceFilter2());
        filterChainManager.addFilter("myAdvFilter2", new MyAdviceFilter2());
        filterChainManager.addFilter("myPathMatchingFilter", new MyPathMatchingFilter());
        //使用自定义的登录拦截器
        filterChainManager.addFilter("myFormLoginFilter", new MyFormLoginFilter());


        //4、注册 URL-Filter 的映射关系
        filterChainManager.addToChain("/unauthorized", "anon");
        filterChainManager.addToChain("/formfilterlogin", "myFormLoginFilter");
        filterChainManager.addToChain("/**", "myFormLoginFilter");
        filterChainManager.addToChain("/logout", "logout");
        filterChainManager.addToChain("/**", "roles", "admin");
        filterChainManager.addToChain("/**", "myFilter1");
        filterChainManager.addToChain("/**", "myAdvFilter1");
        filterChainManager.addToChain("/**", "myAdvFilter2");

        /**
         * /** 就是注册给 PathMatchingFilter的url模式，config 就是拦截器的配置参数，多个之间逗号分隔
         * onPreHandle 使用 mappedValue 接收参数值。
         */
        filterChainManager.addToChain("/**", "myPathMatchingFilter", "configA,configB");

        //5、设置 Filter 的属性
        FormAuthenticationFilter authcFilter = (FormAuthenticationFilter) filterChainManager.getFilter("authc");
        authcFilter.setLoginUrl("/formfilterlogin");

        MyFormLoginFilter myFormLoginFilter = (MyFormLoginFilter) filterChainManager.getFilter("myFormLoginFilter");
        myFormLoginFilter.setLoginUrl("/formfilterlogin");

        RolesAuthorizationFilter rolesFilter = (RolesAuthorizationFilter) filterChainManager.getFilter("roles");
        rolesFilter.setUnauthorizedUrl("/unauthorized");

        filterChainResolver.setFilterChainManager(filterChainManager);
        return filterChainResolver;
    }
}