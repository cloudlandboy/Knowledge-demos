package cn.clboy.demo.springboot.alipay.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response implements Serializable {
    private Boolean success;
    private String message;
    private Object Data;

    public static Response success(String message, Object data) {
        return new Response(true, message, data);
    }

    public static Response success(Object data) {
        return new Response(true, null, data);
    }

    public static Response error(String message, Object data) {
        return new Response(false, message, data);
    }

    public static Response error(String message) {
        return new Response(false, message, null);
    }

}