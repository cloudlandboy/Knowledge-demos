package cn.clboy.demo.springboot.embedded.web.servers.configuration;

import cn.clboy.demo.springboot.embedded.web.servers.Servlet.DemoRegisterServlet01;
import cn.clboy.demo.springboot.embedded.web.servers.filter.ResponseContentTypeFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * @Author clboy
 * @Date 2021/2/24 上午11:07
 * @Since 1.0.0
 */
@Configuration
public class ServerConfiguration {

    @Bean
    public ServletRegistrationBean regServlet01() {
        return new ServletRegistrationBean<DemoRegisterServlet01>(new DemoRegisterServlet01(), "/regServlet01");
    }

    @Bean
    public FilterRegistrationBean responseContentTypeFilter() {
        FilterRegistrationBean<ResponseContentTypeFilter> register = new FilterRegistrationBean<>();
        register.setFilter(new ResponseContentTypeFilter());
        register.setUrlPatterns(Arrays.asList("/*"));
        return register;
    }
}