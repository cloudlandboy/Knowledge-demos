package cn.clboy.demo.shiro.web.p02.filter;

import org.apache.shiro.web.filter.PathMatchingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Arrays;

/**
 * PathMatchingFilter 继承了 AdviceFilter，提供了 url 模式过滤的功能，如果需要对指定的请求进行处理，可以扩展 PathMatchingFilter：
 */
public class MyPathMatchingFilter extends PathMatchingFilter {

    /**
     * preHandle：会进行 url 模式与请求 url 进行匹配，如果匹配会调用 onPreHandle；如果没有配置url模式/没有 url模式匹配，默认直接返回 true
     * onPreHandle：如果 url 模式与请求 url 匹配，那么会执行 onPreHandle，并把该拦截器配置的参数传入。默认什么不处理直接返回 true。
     */
    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        System.out.println(Arrays.toString((String[]) mappedValue));
        return true;
    }
}