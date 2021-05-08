package cn.clboy.demo.springboot.weixinpay.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Data
@XmlRootElement
@AllArgsConstructor
@NoArgsConstructor
public class WeiXinNotifyResult implements Serializable {

    private String return_code;
    private String return_msg;

    public static WeiXinNotifyResult success() {
        return new WeiXinNotifyResult("SUCCESS", "OK");
    }

    public static WeiXinNotifyResult fail(String message) {
        return new WeiXinNotifyResult("FAIL", message);
    }
}