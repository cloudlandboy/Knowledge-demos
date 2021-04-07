package cn.clboy.demo.springboot.embedded.web.servers.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import java.io.IOException;

/**
 * @Author clboy
 * @Date 2021/2/24 上午11:33
 * @Since 1.0.0
 */
public class ResponseContentTypeFilter extends HttpFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        super.doFilter(request, response, chain);
    }
}