package cn.clboy.demo.springboot.alipay.repository;


import cn.clboy.demo.springboot.alipay.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}