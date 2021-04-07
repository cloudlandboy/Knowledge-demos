package cn.clboy.demo.shiro.web.p02.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
public class MyFormLoginFilter extends AccessControlFilter {

    private String successUrl = "/";


    /**
     * 首先判断是否已经登录过了，如果已经登录过了继续拦截器链即可；
     * 如果没有登录，返回false，执行 onAccessDenied 方法
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        boolean isAuthenticated = SecurityUtils.getSubject().isAuthenticated();
        return isAuthenticated;
    }


    /**
     * 未登录访问拒绝
     * 看看是否是登录请求，如果是 get 方法的登录页面请求，则继续拦截器链（到请求页面），否则如果是 get 方法的其他页面请求则保存当前请求并重定向到登录页面；
     * 如果是 post 方法的登录页面表单提交请求，则收集用户名 / 密码登录即可，如果失败了保存错误消息到 “shiroLoginFailure” 并返回到登录页面；
     * 如果登录成功了，且之前有保存的请求，则重定向到之前的这个请求，否则到默认的成功页面。
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if (this.isLoginRequest(req, resp)) {
            //form表单提交
            if (POST_METHOD.equalsIgnoreCase(req.getMethod())) {
                //登录
                if (this.login(req)) {
                    //登录成功则重定向到之前的请求，否则到默认的成功页面。，后面的拦截器不执行
                    return redirectToSuccessUrl(req, resp);
                }
            }
            //如果是登录页面的GET请求或者登录失败继续执行到登录页面
            return true;
        } else {
            //未登录并且非登录页面请求,保存当前地址并重定向到登录界面
            saveRequestAndRedirectToLogin(request, response);
            return false;
        }
    }


    private boolean login(HttpServletRequest req) {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        try {
            SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password));
        } catch (Exception e) {
            req.setAttribute("shiroLoginFailure", e.getClass());
            return false;
        }
        return true;
    }

    private boolean redirectToSuccessUrl(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebUtils.redirectToSavedRequest(req, resp, successUrl);
        return false;
    }
}