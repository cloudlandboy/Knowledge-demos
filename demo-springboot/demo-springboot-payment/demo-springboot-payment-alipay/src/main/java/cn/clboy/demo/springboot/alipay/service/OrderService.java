package cn.clboy.demo.springboot.alipay.service;


import cn.clboy.demo.springboot.alipay.entity.Order;
import cn.clboy.demo.springboot.alipay.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public Order addOrder(Order order) {


        order.setStatus(Order.OrderStatus.UNPAY);
        order.setCreateTime(new Date());
        orderRepository.save(order);
        return order;
    }

    public Order getOrder(Long orderId) {
        Optional<Order> optional = orderRepository.findById(orderId);
        return optional.isPresent() ? optional.get() : null;
    }

    public List<Order> getAllOrder(int page, int size) {
        Sort sort = Sort.by("createTime").descending();
        PageRequest pageRequest = PageRequest.of(page - 1, size, sort);
        Page<Order> pageResult = orderRepository.findAll(pageRequest);
        return pageResult.getContent();
    }

    public void changeStatus(Long orderId, Integer status) {
        Order order = orderRepository.getOne(orderId);
        order.setStatus(status);
        orderRepository.save(order);
    }
}