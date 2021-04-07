package cn.clboy.demo.shiro.springboot.configuration;

import com.jagregory.shiro.freemarker.ShiroTags;
import freemarker.template.Configuration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 配置freemarker中的shiro标签
 *
 * @Author clboy
 * @Date 2021/2/25 下午4:14
 * @since 1.0.0
 */
@Component
public class FreeMarkerShiroTagsConfigurer implements InitializingBean {

    @Autowired
    private Configuration configuration;

    @Override
    public void afterPropertiesSet() throws Exception {
        configuration.setSharedVariable("shiro", new ShiroTags());
    }
}