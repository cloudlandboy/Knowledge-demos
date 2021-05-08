package cn.clboy.demo.springboot.alipay.controller;


import cn.clboy.demo.springboot.alipay.vo.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ControllerExceptionHandle {

    @ResponseBody
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Response paramException() {
        return Response.error("请求参数格式不正确！");
    }
}