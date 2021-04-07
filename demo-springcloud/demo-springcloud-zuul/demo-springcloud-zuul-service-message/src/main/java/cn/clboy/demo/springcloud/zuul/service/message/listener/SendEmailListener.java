package cn.clboy.demo.springcloud.zuul.service.message.listener;


import cn.clboy.demo.springcloud.zuul.service.message.helper.EmailHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 模拟监听消息
 */
@RestController
public class SendEmailListener {

    @Autowired
    private EmailHelper emailHelper;

    @GetMapping(value = "/email", produces = "text/plain")
    public String messageHandle(String msg, String receiver, HttpServletResponse response) throws InterruptedException {
        if (StringUtils.isAnyBlank(msg, receiver)) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return "msg or receiver is blank";
        }
        emailHelper.sendEmail(msg, receiver);
        return "收到消息，已做处理。";
    }


}