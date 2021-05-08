package cn.clboy.demo.springboot.alipay.controller;

import cn.clboy.demo.springboot.alipay.config.AlipayProperties;
import cn.clboy.demo.springboot.alipay.entity.Order;
import cn.clboy.demo.springboot.alipay.service.OrderService;
import cn.clboy.demo.springboot.alipay.vo.Response;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.ijpay.alipay.AliPayApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    AlipayProperties alipayProperties;

    @PutMapping
    public Order addOrder(@RequestBody Order order) {
        return orderService.addOrder(order);
    }

    @GetMapping("/page/{size}/{page}")
    public List<Order> getAllOrder(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return orderService.getAllOrder(page, size);
    }

    @GetMapping("/detail/{orderId}")
    public Order orderDetail(@PathVariable("orderId") Long orderId) {
        return orderService.getOrder(orderId);
    }

    @GetMapping("/pay/pc")
    public void pay(Long orderId, HttpServletResponse response) throws IOException, AlipayApiException {

        //查询订单
        Order order = orderService.getOrder(orderId);

        AlipayTradePagePayModel model = new AlipayTradePagePayModel();

        //订单号
        model.setOutTradeNo(order.getId().toString());
        //销售产品码 目前只支持FAST_INSTANT_TRADE_PAY
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        //订单总金额，单位为元，精确到小数点后两位
        model.setTotalAmount(order.getPrice().toString());
        model.setSubject("支付宝商城测试订单");
        model.setBody("支付宝商城测试");

        //对于appid等参数会从AliPayApiConfigKit中获取，在AlipayConfig中已经添加进去了
        AliPayApi.tradePage(response, model, alipayProperties.getNotifyUrl(), alipayProperties.getReturnUrl());

        log.info("网页已经生成，如果提示支付存在风险，检查是不是登录了支付宝的网页，最粗暴的方式是关闭浏览器再打开。");
    }

    @PostMapping(value = "/notify")
    public void payNotify(HttpServletRequest request) throws AlipayApiException {
        // 获取支付宝POST过来反馈信息
        Map<String, String> params = AliPayApi.toMap(request);

        for (Map.Entry<String, String> entry : params.entrySet()) {
            log.info(entry.getKey() + " = " + entry.getValue());
        }

        boolean verifyResult = AlipaySignature.rsaCheckV1(params, alipayProperties.getAliPayPublicKey(), "UTF-8", "RSA2");

        Long orderId = Long.parseLong(params.get("out_trade_no"));
        if (verifyResult) {
            String tradeStatus = params.get("trade_status");

            if (AlipayProperties.TradeStatus.TRADE_SUCCESS.equalsIgnoreCase(tradeStatus)) {
                orderService.changeStatus(orderId, Order.OrderStatus.PAYED);
            }

            log.info("notify_url 验证成功succcess");
        } else {
            log.error("notify_url 验证失败");
            // TODO
        }

    }

    @GetMapping("/status")
    public Response orderStatus(Long orderId) {
        Order order = orderService.getOrder(orderId);
        return Response.success(order.getStatus());
    }
}