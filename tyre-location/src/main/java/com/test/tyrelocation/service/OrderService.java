package com.test.tyrelocation.service;

import com.test.tyrelocation.common.page.OrderPageBean;

import java.util.Map;

/**
 * @Date: 2019/11/22 15:57
 * @Author: JackLei
 * @Description:
 * @Version:
 */
public interface OrderService {

    int getPayOrderCount(Long userId);

    Map<String, Integer> getPastForMonth(int num, Long userId);

    int getPastToday(Long userId);

    int getPastCurrentMonth(Long userId);

    OrderPageBean getListByPage(Long userId, OrderPageBean orderPageBean);

    void walletPay(int sumMoney,int number,Long apiId, Long voucherId, Long userId);

    void cancelOrder(Long orderId, Long userId);
}
