# 支付的demo

## 添加其他测试

### 第一步

编写后端java代码,service,dao层一般不用动



### 第二步，修改index.html

在PayHelper类添加支付处理方法

```
platform{
	平台名:{
		付款方式：{
			//自定义的一些属性
			//....
			//支付处理函数
			handle(_this, orderId, options)
		}
	}
}
```

- _this：PayHelper的实例对象
- orderId：订单号
- options：自定义配置，需要在handle方法中用到的调用时传过来（如一些回调函数）

### 第三步，在生成订单后调用相应的支付方法

> 格式：PayHelper的实例.pay(平台名,付款方式,订单id,自定义配置)

```js
payHandle() {
    this.createLoading("提交订单中...");
    axios.put('/', this.order).then(res => {
        // 关闭购物车
        this.dialogTableVisible = false;
        const orderId = res.data.id;
        this.$message.success('订单生已生成！');
        
        //在此处调用相应的支付方法
         this.payHelper.pay("aliPay", "pc", orderId,{})
    }).catch(this.commonCatch);
}
```



## 支付宝支付

> 支付宝沙箱环境

### pc网页支付

```js
aliPay: {
    pc: {
        api: 'order/pay/pc',
        handle(_this, orderId, options) {
            location.href = this.api + '?orderId=' + orderId;
        }
    }
}
```

```js
this.payHelper.pay(this.selectorPay.platform, this.selectorPay.method, orderId, {})
```


## 微信支付

> 真实环境

### 扫码支付

```js
wxPay: {
    scanCode: {
        api: '/pay/scan_code',
        intervalQuery: null,
        intervalQueryCount: 0,

        /**
         * @param _this
         * @param orderId
         * @param options
         * {
         *  qrCodeSuccess：获取到二维码链接后的回调
         *  interval：轮询订单状态时间间隔
         *  orderLiveTime：最长轮询时间，毫秒
         *  orderStatusQueryError：订单查询出错处理函数
         *  orderFinished：轮询中调用改函数，根据服务器返回的结果判断是否已收到回调并处理，返回boolean
         *  orderQueryDone：轮询完毕后的回调
         *  }
         */
        handle(_this, orderId, options) {
            this.intervalQueryCount = 0;
            _this.$vue.createLoading("等待支付二维码生成...");
            axios.post(this.api, {}, {
                params: {
                    orderId: orderId
                }
            }).then(res => {
                res = res.data;
                if (res.success) {
                    this.codeUrl = res.data;

                    //生成二维码之后回调
                    options.qrCodeSuccess(this.codeUrl);

                    //轮询间隔
                    this.interval = options.interval || 1000;
                    //最大轮询时间,默认3分钟
                    let orderLiveTime = options.orderLiveTime || 3
                    this.maxCount = (1000 * 60 * orderLiveTime) / this.interval;

                    //轮询获取订单状态
                    this.intervalQuery = setInterval(() => {
                        this.intervalQueryCount++;
                        if (_this.orderFinished || this.intervalQueryCount >= this.maxCount) {
                            clearInterval(this.intervalQuery);
                            options.orderQueryDone && options.orderQueryDone({
                                orderFinished: _this.orderFinished,
                                timeOut: this.intervalQueryCount >= this.maxCount
                            });
                        }
                        _this.orderStatusQuery(orderId, options);
                    }, this.interval);
                } else {
                    _this.commonErrorHandle(options, "qrCodeError", res.message);
                }
            }).catch((err) => {
                _this.commonErrorHandle(options, "qrCodeError", err.message);
            });
        }
    }
},
```

```js
this.payHelper.pay(this.selectorPay.platform, this.selectorPay.method, orderId, {
    qrCodeSuccess(codeUrl) {
        app.closeLoading();
        app.resetOrder();
        app.resetCart();
        app.qrcodeUrl = codeUrl;
        app.scanCodePayDialog = true;
    },
    orderFinished(res) {
        if (res.data == 1) {
            app.$message.success('支付完成！');
            return true;
        }
    },
    orderQueryDone(result) {
        app.scanCodePayDialog = false;
        if (result.timeOut) {
            app.$notify({
                title: '提示',
                message: '订单支付超时！',
                duration: 0,
                type: 'warning'
            });
        }
    }
})
```