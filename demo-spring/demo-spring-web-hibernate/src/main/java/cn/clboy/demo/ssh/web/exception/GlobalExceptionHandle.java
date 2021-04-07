package cn.clboy.demo.ssh.web.exception;

import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(BindException.class)
    public void paramException(BindException exception, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        String message = exception.getFieldError().getDefaultMessage();
        response.getWriter().write(message);
    }

}