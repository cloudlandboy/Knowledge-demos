package cn.clboy.demo.springboot.cors.config;


import cn.clboy.demo.springboot.cors.Interceptor.CookieIntercetor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                //默认只支持get，head，post请求,默认Origins为*
                CorsRegistration corsRegistration = registry.addMapping("/registry/**");
                //corsRegistration.allowCredentials(true);
                //corsRegistration.allowedOrigins("http://test.com:5500");
                //corsRegistration.allowedMethods("*");
            }
        };
    }


    /**
     * 如果配置了CorsFilter，访问的请求被程序判断是cors请求
     * 而没有使用registerCorsConfiguration注册配置路径映射，请求会直接被拦截，拒绝请求
     * 例如本例中/registry/**，/anno/**即使配置了跨域，但是请求在CorsFilter这里就被拒绝了后面的没有执行到
     * 因此上面的配置也就失效了
     * <p>
     * 但是registerCorsConfiguration方法可以针对不同的url添加不同的配置（config）
     * <p>
     * 所以使用了上面的方式就不要再使用CorsFilter了;
     *
     * @return
     */
    //@Bean
    public CorsFilter corsFilter() {

        //添加CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        //config.applyPermitDefaultValues();

        //允许的域，如果要发送Cookie，就不能设为星号，必须指定明确的允许Origin（这里配置为*可以，spring会帮我们解决）
        config.addAllowedOrigin("*");
        //是否允许携带Cookie信息（allowedOrigin为*，allowCredentials为true，spring会把响应中的Access-Control-Allow-Origin改为实际请求的origin）
        config.setAllowCredentials(false);
        //允许的请求方式
        config.addAllowedMethod("*");
        //允许的头信息
        config.addAllowedHeader("*");

        //添加映射路径
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/filter/**", config);
        //返回新的CorsFilter.
        return new CorsFilter(configSource);
    }

    @Bean
    public WebMvcConfigurer cookieInterceptor() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new CookieIntercetor()).addPathPatterns("/**");
            }
        };
    }
}
