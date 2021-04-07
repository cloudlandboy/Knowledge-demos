package cn.clboy.demo.shiro.web.p02.filter;

import org.apache.shiro.web.servlet.AdviceFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MyAdviceFilter2 extends AdviceFilter {
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        System.out.println(getName() +"====预处理/前置处理");
        //返回 false 将中断后续拦截器链的执行
        return true;
    }
    @Override
    protected void postHandle(ServletRequest request, ServletResponse response) throws Exception {
        System.out.println(getName() +"====后处理/后置返回处理");
    }
    @Override
    public void afterCompletion(ServletRequest request, ServletResponse response, Exception exception) throws Exception {
        System.out.println(getName() +"====完成处理/后置最终处理");
    }
}