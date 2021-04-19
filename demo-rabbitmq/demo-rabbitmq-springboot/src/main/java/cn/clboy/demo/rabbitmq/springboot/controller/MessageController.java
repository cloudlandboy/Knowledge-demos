package cn.clboy.demo.rabbitmq.springboot.controller;


import cn.clboy.demo.rabbitmq.springboot.config.MQConfig;
import cn.clboy.demo.rabbitmq.springboot.pojo.VerifyCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;


@Controller
@Api(tags = "测试发送消息接口")
@RequestMapping("/msg")
public class MessageController {

    @Autowired
    AmqpTemplate amqpTemplate;

    @ApiOperation("基本消息模型")
    @PostMapping("/basic")
    public ResponseEntity<String> basic(String code) {
        VerifyCode verifyCode = new VerifyCode(code, new Date());
        amqpTemplate.convertAndSend("", MQConfig.BASIC_QUEUE, verifyCode);
        return ResponseEntity.ok("[basic] 消息已经发送！");
    }

    @ApiOperation("fanout 广播消息模型")
    @PostMapping("/fanout")
    public ResponseEntity<String> fanout(String code) {
        VerifyCode verifyCode = new VerifyCode(code, new Date());
        amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE, "", verifyCode);
        return ResponseEntity.ok("[fanout] 消息已经发送！");
    }

    @ApiOperation("direct 路由消息模型，发送给路由：email")
    @PostMapping("/direct/email")
    public ResponseEntity<String> directEmail(String code) {
        VerifyCode verifyCode = new VerifyCode(code, new Date());
        amqpTemplate.convertAndSend(MQConfig.DIRECT_EXCHANGE, "email", verifyCode);
        return ResponseEntity.ok("[direct:email] 消息已经发送！");
    }

    @ApiOperation("direct 路由消息模型，发送给路由：phone")
    @PostMapping("/direct/phone")
    public ResponseEntity<String> directPhone(String code) {
        VerifyCode verifyCode = new VerifyCode(code, new Date());
        amqpTemplate.convertAndSend(MQConfig.DIRECT_EXCHANGE, "phone", verifyCode);
        return ResponseEntity.ok("[direct:phone] 消息已经发送！");
    }

    @ApiOperation("topic 通配符路由消息模型，发送给路由：user.role.add")
    @PostMapping("/topic/user_role_add")
    public ResponseEntity<String> topicUserRoleAdd(String code) {
        VerifyCode verifyCode = new VerifyCode(code, new Date());
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "user.role.add", verifyCode);
        return ResponseEntity.ok("[topic:user.role.add] 消息已经发送！");
    }

    @ApiOperation("topic 通配符路由消息模型，发送给路由：user.role.del")
    @PostMapping("/topic/user_role_del")
    public ResponseEntity<String> topicUserRoleDel(String code) {
        VerifyCode verifyCode = new VerifyCode(code, new Date());
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "user.role.del", verifyCode);
        return ResponseEntity.ok("[topic:user.role.del] 消息已经发送！");
    }

    @ApiOperation("topic 通配符路由消息模型，发送给路由：item.category.add")
    @PostMapping("/topic/item_category_add")
    public ResponseEntity<String> topicItemCategoryAdd(String code) {
        VerifyCode verifyCode = new VerifyCode(code, new Date());
        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "item.category.add", verifyCode);
        return ResponseEntity.ok("[topic:item.category.add] 消息已经发送！");
    }

    @ApiOperation("测试发送到一个不存在的exchange")
    @PostMapping("/test/unknown_exchange")
    public ResponseEntity<String> unknownExchange(String code) {
        amqpTemplate.convertAndSend("unknown_exchange", "", new VerifyCode(code, new Date()));
        return ResponseEntity.ok("消息已经发送！");
    }

    @ApiOperation("测试发送到一个未绑定队列的交换机")
    @PostMapping("/test/un_bind_exchange")
    public ResponseEntity<String> unBindExchange(String code) {
        amqpTemplate.convertAndSend("un_bind_exchange", "test", new VerifyCode(code, new Date()));
        return ResponseEntity.ok("消息已经发送！");
    }
}