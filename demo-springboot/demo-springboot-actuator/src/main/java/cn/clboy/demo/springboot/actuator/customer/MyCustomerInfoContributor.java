package cn.clboy.demo.springboot.actuator.customer;


import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

/**
 * @author clboy
 * 定制info信息
 * <p>
 * 和配置文件中的共存，一样的会覆盖掉配置文件中的
 */
@Component
public class MyCustomerInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("appName", "demo-springboot-actuator")
                .withDetail("author", "clboy")
                .withDetail("github", "https://github.com/cloudlandboy")
                .withDetail("email", "syl@clboy.cn");
    }
}