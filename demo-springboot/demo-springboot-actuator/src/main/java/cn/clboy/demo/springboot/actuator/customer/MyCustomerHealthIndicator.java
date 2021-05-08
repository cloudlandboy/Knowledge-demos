package cn.clboy.demo.springboot.actuator.customer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;


/**
 * 定制 Health 信息,
 */
@Component
public class MyCustomerHealthIndicator extends AbstractHealthIndicator {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        System.out.println("开始健康检查......");
        Long order = redisTemplate.boundValueOps("order").getExpire();
        if (order > 0) {
            builder.up(); //健康状态
        } else if (order > -2) {
            builder.down(); //宕机状态
        } else {
            builder.status(Status.UNKNOWN); //未知状态
        }
        builder.withDetail("剩余时间", order)
                .withDetail("大于0", "健康状态")
                .withDetail("大于-2", "宕机状态")
                .withDetail("其他", "未知状态");
    }
}