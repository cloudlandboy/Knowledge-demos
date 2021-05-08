package cn.clboy.demo.springboot.weixinpay.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 模拟订单项
 */
@Getter
@Setter
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItem implements Serializable {


    @Id
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @GenericGenerator(name = "snowflake", strategy = "cn.clboy.demo.springboot.weixinpay.utils.SnowflakeIdGenerator")
    @GeneratedValue(generator = "snowflake")
    private Long id;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 商品数量
     */
    private Integer num;

    /**
     * 小计
     */
    private BigDecimal total;

    /**
     * 商品名
     */
    private String name;

    /**
     * 所属订单
     */
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonIgnoreProperties("orderItems")
    private Order order;
}