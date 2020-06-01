package com.test.tyreadmin.service;

import com.test.tyreadmin.entity.Order;
import com.test.tyreadmin.repository.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Date: 2020/4/22 20:33
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<Order> getOrderByUserId(Long userId) {
        List<Order> orders = orderMapper.selectByUserId(userId);
        return orders;
    }
}
