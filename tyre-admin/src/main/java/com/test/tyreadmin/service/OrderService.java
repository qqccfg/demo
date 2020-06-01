package com.test.tyreadmin.service;

import com.test.tyreadmin.entity.Order;
import java.util.List;

/**
 * @Date: 2020/4/22 20:31
 * @Author: JackLei
 * @Description:
 * @Version:
 */
public interface OrderService {
    List<Order> getOrderByUserId(Long userId);
}
