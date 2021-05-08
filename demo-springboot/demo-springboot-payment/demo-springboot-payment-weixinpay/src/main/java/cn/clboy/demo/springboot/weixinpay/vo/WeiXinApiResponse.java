package cn.clboy.demo.springboot.weixinpay.vo;


import lombok.Data;

import java.io.Serializable;

/**
 * 微信api返回结果
 */
@Data
public class WeiXinApiResponse implements Serializable {

    public static final String SUCCESS_FLAG = "SUCCESS";

    private String return_code;
    private String return_msg;
    private String appid;
    private String mch_id;
    private String device_info;
    private String nonce_str;
    private String sign;
    private String result_code;
    private String err_code;
    private String err_code_des;
    private String trade_type;
    private String prepay_id;
    private String code_url;


    public boolean returnIsSuccess() {
        return SUCCESS_FLAG.equalsIgnoreCase(this.return_code);
    }

    public boolean resultIsSuccess() {
        return SUCCESS_FLAG.equalsIgnoreCase(this.result_code);
    }
}