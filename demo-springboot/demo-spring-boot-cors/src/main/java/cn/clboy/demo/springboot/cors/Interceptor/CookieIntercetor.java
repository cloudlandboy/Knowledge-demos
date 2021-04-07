package cn.clboy.demo.springboot.cors.Interceptor;


import cn.hutool.extra.servlet.ServletUtil;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class CookieIntercetor extends HandlerInterceptorAdapter {

    /**
     * 测试跨域能不能获取到cookie
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, Cookie> cookieMap = ServletUtil.readCookieMap(request);
        System.out.println(cookieMap.size());
        cookieMap.forEach((key, value) -> System.out.println(key + " : " + value.getValue()));
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String value = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HH_mm_ss"));
        //domain使用127.0.0.1和localhost无效
        ServletUtil.addCookie(response, "res-cookie", value, 3600, "", "test.com");
    }
}