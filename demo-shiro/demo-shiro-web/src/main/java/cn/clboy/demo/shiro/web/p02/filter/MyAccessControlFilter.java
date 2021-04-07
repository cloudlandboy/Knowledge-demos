package cn.clboy.demo.shiro.web.p02.filter;

import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * AccessControlFilter 继承了 PathMatchingFilter，并扩展了了两个方法：
 * - isAccessAllowed：即是否允许访问，返回 true 表示允许；
 * - onAccessDenied：表示访问拒绝时是否自己处理，如果返回 true 表示自己不处理且继续拦截器链执行，返回 false 表示自己已经处理了（比如重定向到另一个页面）。
 */
public class MyAccessControlFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        System.out.println("access allowed");
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        System.out.println("访问拒绝也不自己处理，继续拦截器链的执行");
        return true;
    }
}