package cn.clboy.demo.springboot.weixinpay.controller;

import cn.clboy.demo.springboot.weixinpay.config.WeiXinPayProperties;
import cn.clboy.demo.springboot.weixinpay.entity.Order;
import cn.clboy.demo.springboot.weixinpay.service.OrderService;
import cn.clboy.demo.springboot.weixinpay.vo.Response;
import cn.clboy.demo.springboot.weixinpay.vo.WeiXinApiResponse;
import cn.clboy.demo.springboot.weixinpay.vo.WeiXinNotifyRequest;
import cn.clboy.demo.springboot.weixinpay.vo.WeiXinNotifyResult;
import cn.hutool.core.bean.BeanUtil;
import com.ijpay.core.enums.SignType;
import com.ijpay.core.enums.TradeType;
import com.ijpay.core.kit.HttpKit;
import com.ijpay.core.kit.WxPayKit;
import com.ijpay.wxpay.WxPayApi;
import com.ijpay.wxpay.model.UnifiedOrderModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    WeiXinPayProperties weiXinPayProperties;

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

    /**
     * 扫码支付，获取链接，前端生成二维码
     *
     * @param orderId
     * @return
     */
    @PostMapping("/pay/scan_code")
    public Response pay(Long orderId) {

        //查询订单
        Order order = orderService.getOrder(orderId);

        int orderPrice = order.getPrice().multiply(BigDecimal.valueOf(100)).intValue();

        log.info("实际交易金额：{}，测试转为0.01", orderPrice);

        Map<String, String> data = UnifiedOrderModel.builder()
                .appid(weiXinPayProperties.getAppid())
                //商户号
                .mch_id(weiXinPayProperties.getMch_id())
                //异步接收微信支付结果通知的回调地址
                .notify_url(weiXinPayProperties.getNotify_url())
                //货币类型
                .fee_type(weiXinPayProperties.getFee_type())
                //随机字符串，这里使用的UUID
                .nonce_str(WxPayKit.generateStr())
                //交易类型
                .trade_type(TradeType.NATIVE.getTradeType())
                // 商品描述
                .body("微信支付测试")
                //订单号
                .out_trade_no(Long.toString(orderId))
                //trade_type=NATIVE时，此参数必传。此参数为二维码中包含的商品ID
                .product_id("测试商品集合")
                //商品详情，非必须
                //.detail(JSONUtil.toJsonStr(order.getOrderItems()))
                //订单金额，单位分
                .total_fee("1")
                //此终端的ip
                .spbill_create_ip("127.0.0.1")
                .build()
                //利用私钥生成签名
                .createSign(weiXinPayProperties.getPrivateKey(), SignType.HMACSHA256);

        String xmlResult = WxPayApi.pushOrder(data);
        Map<String, String> result = WxPayKit.xmlToMap(xmlResult);

        WeiXinApiResponse weiXinApiResult = BeanUtil.mapToBean(result, WeiXinApiResponse.class, false, null);

        BeanUtils.copyProperties(result, weiXinApiResult);

        // 通信结果
        if (!weiXinApiResult.returnIsSuccess()) {
            log.error(weiXinApiResult.getReturn_msg());
            return Response.error(weiXinApiResult.getReturn_msg());
        }

        // 业务处理结果
        if (!weiXinApiResult.resultIsSuccess()) {
            String message = weiXinApiResult.getErr_code().concat(":").concat(weiXinApiResult.getErr_code_des());
            log.error("调用微信api出错了，调用信息：{}", result);
            return Response.error(message);
        }

        // 成功
        log.info("prepay_id：{}，二维码链接：{}", weiXinApiResult.getPrepay_id(), weiXinApiResult.getCode_url());
        return Response.success(weiXinApiResult.getCode_url());
    }

    /**
     * 接收微信的异步通知
     * 指定返回xml类型
     */
    @PostMapping(value = "/notify", produces = MediaType.APPLICATION_XML_VALUE)
    public WeiXinNotifyResult payNotify(HttpServletRequest request) {

        String xmlResult = HttpKit.readData(request);

        Map<String, String> params = WxPayKit.xmlToMap(xmlResult);

        WeiXinNotifyRequest weiXinNotifyRequest = BeanUtil.mapToBean(params, WeiXinNotifyRequest.class, false, null);


        log.info("参数列表：{}", params);

        //验签,指定SignType与申请预付款时一致

        boolean verifySuccess = WxPayKit.verifyNotify(params,
                weiXinPayProperties.getPrivateKey(),
                SignType.HMACSHA256);

        log.info("验签结果：{}", verifySuccess);

        if (!verifySuccess) {
            //验证未通过
            return WeiXinNotifyResult.fail("签名失败");
        }

        if (weiXinNotifyRequest.returnIsSuccess() && weiXinNotifyRequest.resultIsSuccess()) {
            //更新数据库
            Long orderId = Long.valueOf(weiXinNotifyRequest.getOut_trade_no());
            orderService.changeStatus(orderId, Order.OrderStatus.PAYED);
        }

        return WeiXinNotifyResult.success();
    }

    @GetMapping("/status")
    public Response orderStatus(Long orderId) {
        Order order = orderService.getOrder(orderId);
        return Response.success(order.getStatus());
    }
}