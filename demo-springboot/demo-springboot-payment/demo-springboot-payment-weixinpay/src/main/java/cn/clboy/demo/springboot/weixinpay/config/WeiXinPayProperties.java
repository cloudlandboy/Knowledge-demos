package cn.clboy.demo.springboot.weixinpay.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "weixin.pay")
public class WeiXinPayProperties {
    /**
     * 账号id
     */
    private String appid;

    private String mch_id;

    /**
     * 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。公网域名必须为https
     * <p>
     * 如果是走专线接入，使用专线NAT IP或者私有回调域名可使用http
     */
    private String notify_url;

    /**
     * 货币类型
     */
    private String fee_type;

    /**
     * 密钥
     */
    private String privateKey;

}