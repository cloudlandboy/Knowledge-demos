package cn.clboy.demo.springboot.weixinpay.vo;


import lombok.Data;

@Data
public class WeiXinNotifyRequest extends WeiXinApiResponse{
    private String sign_type;
    private String openid;
    private String is_subscribe;
    private String total_fee;
    private String settlement_total_fee;
    private String fee_type;
    private String cash_fee;
    private String cash_fee_type;
    private String coupon_fee;
    private String coupon_count;
    private String coupon_type_$n;
    private String coupon_id_$n;
    private String coupon_fee_$n;
    private String transaction_id;
    private String out_trade_no;
    private String attach;
    private String time_end;
}