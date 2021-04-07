package cn.clboy.demo.shiro.web.p02.filter;

import org.apache.shiro.web.servlet.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 *  OncePerRequestFilter 保证一次请求只调用一次 doFilterInternal，即如内部的 forward 不会再多执行一次 doFilterInternal
 *  具体逻辑见OncePerRequestFilter的doFilter方法
 *
 */
public class MyOncePerRequestFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("=========once per request filter");
        chain.doFilter(request, response);
    }
}