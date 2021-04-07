package cn.clboy.demo.shiro.spring.web.exception;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Author clboy
 * @Date 2021/2/1 下午3:53
 * @Since 1.0.0
 */
@ControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handle(AuthorizationException e, Model model) {
        return handleMsg(model, "未认证或未授权账户");
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handle(Exception e, Model model) {
        return handleMsg(model, e.getMessage());
    }

    private String handleMsg(Model model, String msg) {
        model.addAttribute("error", msg == null ? "未知错误" : msg);
        return "error";
    }

}