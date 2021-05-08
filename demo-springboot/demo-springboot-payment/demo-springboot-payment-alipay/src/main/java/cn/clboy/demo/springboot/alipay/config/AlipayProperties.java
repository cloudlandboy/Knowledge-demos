package cn.clboy.demo.springboot.alipay.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "ali.pay")
public class AlipayProperties {

    /**
     * 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
     */
    private String appId;

    /**
     * 商户私钥，您的PKCS8格式RSA2私钥
     */
    private String privateKey;

    /**
     * 支付宝公钥
     */
    private String aliPayPublicKey;

    /**
     * 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     */
    private String notifyUrl;

    /**
     * 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     */
    private String returnUrl;

    /**
     * 签名方式
     */
    private String signType = "RSA2";

    /**
     * 字符编码格式
     */
    private String charset = "utf-8";

    /**
     * 支付宝网关
     */
    private String gatewayUrl = "https://openapi.alipay.com/gateway.do";


    /**
     * 交易状态
     */
    public interface TradeStatus {
        /**
         * 交易关闭 0
         */
        String TRADE_CLOSED = "TRADE_CLOSED";
        /**
         * 交易完结 0
         */
        String TRADE_FINISHED = "TRADE_FINISHED";
        /**
         * 支付成功    1
         */
        String TRADE_SUCCESS = "TRADE_SUCCESS";
        /**
         * 交易创建    0
         */
        String WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
    }
}