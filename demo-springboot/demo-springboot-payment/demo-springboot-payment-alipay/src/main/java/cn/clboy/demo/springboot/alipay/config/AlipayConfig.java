package cn.clboy.demo.springboot.alipay.config;


import com.ijpay.alipay.AliPayApiConfig;
import com.ijpay.alipay.AliPayApiConfigKit;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AlipayProperties.class)
public class AlipayConfig implements InitializingBean {

    AlipayProperties alipayProperties;

    public AlipayConfig(AlipayProperties alipayProperties) {
        this.alipayProperties = alipayProperties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        AliPayApiConfig aliPayApiConfig = AliPayApiConfig
                .builder()
                .setAppId(alipayProperties.getAppId())
                .setAliPayPublicKey(alipayProperties.getAliPayPublicKey())
                .setPrivateKey(alipayProperties.getPrivateKey())
                .setServiceUrl(alipayProperties.getGatewayUrl())
                .setSignType(alipayProperties.getSignType())
                .setCharset(alipayProperties.getCharset())
                .build();

        // 全局单例
        AliPayApiConfigKit.putApiConfig(aliPayApiConfig);
    }
}