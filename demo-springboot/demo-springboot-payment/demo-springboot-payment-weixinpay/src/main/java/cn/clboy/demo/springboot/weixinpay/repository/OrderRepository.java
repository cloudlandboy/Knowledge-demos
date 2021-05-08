package cn.clboy.demo.springboot.weixinpay.repository;


import cn.clboy.demo.springboot.weixinpay.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}