package com.test.tyrelocation.common.aspect;

import com.test.tyrelocation.common.constant.Constants;
import com.test.tyrelocation.common.emum.ConfigEnum;
import com.test.tyrelocation.entity.Order;
import com.test.tyrelocation.repository.OrderMapper;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

/**
 * @Date: 2019/11/22 17:08
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Aspect
@Component
public class OrderAspect {

    @Autowired
    private OrderMapper orderMapper;

    @Pointcut("execution(* com.test.tyrelocation.repository.OrderMapper.selectByUserId(..)) && args(userId)")
    private void pointcut(Long userId){}

    @Before(value = "pointcut(userId)")
    public void updatePayStatus(Long userId){
        List<Order> orders = orderMapper.selectByUserIdAndUnpaid(userId);
        Date currentTime = new Date();
        List<Order> handleOrders = orders.stream().filter(order -> {
            return currentTime.getTime()-order.getOrderTime().getTime() >= ConfigEnum.ORDER_EXPIRE.getCode();
        }).map(order -> {
            order.setStatus(Constants.NON_PAYMENT);
            return order;
        }).collect(Collectors.toList());
        if (handleOrders.size()>0){
            for (Order order : handleOrders){
                orderMapper.updateStatusByPrimaryKey(order);
            }

        }
    }

}
