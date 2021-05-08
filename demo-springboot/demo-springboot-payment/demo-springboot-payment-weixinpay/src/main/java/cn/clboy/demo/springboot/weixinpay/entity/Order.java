package cn.clboy.demo.springboot.weixinpay.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * 模拟订单,order是关键字不能使用
 * <p>
 * 不要使用@Data注解生成toString,双向关联会造成死循环
 */
@Getter
@Setter
@Entity
@Table(name = "t_order")
public class Order implements Serializable {


    @Id
    @JsonFormat(shape = JsonFormat.Shape.STRING) //序列化为字符串，不然长度超出，js会精度丢失
    @GenericGenerator(name = "snowflake", strategy = "cn.clboy.demo.springboot.weixinpay.utils.SnowflakeIdGenerator")
    @GeneratedValue(generator = "snowflake")
    private Long id;

    private Date createTime;

    private BigDecimal price;

    /**
     * 支付状态 0:未支付,1:已支付,2支付失败
     */
    private Integer status;

    /**
     * 乐观锁
     */
    @Version
    private Integer version;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnoreProperties("order")
    private Set<OrderItem> orderItems;

    public static interface OrderStatus {
        Integer PAYED = 1;
        Integer UNPAY = 0;
        Integer PAYERROR = 2;

    }
}