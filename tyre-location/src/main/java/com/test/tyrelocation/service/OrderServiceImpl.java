package com.test.tyrelocation.service;

import com.test.tyrelocation.common.Exception.TyreException;
import com.test.tyrelocation.common.constant.Constants;
import com.test.tyrelocation.common.emum.ExceptionEnum;
import com.test.tyrelocation.common.emum.MessageEnum;
import com.test.tyrelocation.common.page.OrderPageBean;
import com.test.tyrelocation.common.tool.Generator;
import com.test.tyrelocation.entity.*;
import com.test.tyrelocation.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Date: 2019/11/22 16:02
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private WalletMapper walletMapper;

    @Autowired
    private VoucherMapper voucherMapper;

    @Autowired
    private ApiLimitNumMapper apiLimitNumMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private ApiMapper apiMapper;

    @Override
    public int getPayOrderCount(Long userId) {
        List<Order> orders = orderMapper.selectByUserId(userId);
        orders =orders.stream().filter(order -> {
            return order.getStatus()== Constants.UNPAID;
        }).collect(Collectors.toList());
        return orders==null ? 0 : orders.size();
    }

    @Override
    public Map<String, Integer> getPastForMonth(int num, Long userId) {
        Map<String, Integer> result = new LinkedHashMap<>(num);
        List<String> key = Generator.getMonth(num);
        List<String> dbKey = Generator.getMonth2(num);
        int index = 0;
        for (String val : dbKey){
            Integer sum = orderMapper.selectSumByYMD(val, "%y-%m",userId);
            if (sum==null){
                sum = 0;
            }
            result.put(key.get(index), sum);
            index++;
        }
        return result;
    }

    @Override
    public int getPastToday(Long userId) {
        Integer sum = orderMapper.selectSumByYMD(Generator.getToday(),"%y-%m-%d", userId);
        if (Objects.isNull(sum)){
            sum = 0;
        }
        return sum;
    }

    @Override
    public int getPastCurrentMonth(Long userId) {
        Integer sum = orderMapper.selectSumByYMD(Generator.getCurrentMoth(),"%y-%m", userId);
        if (Objects.isNull(sum)){
            sum = 0;
        }
        return sum;
    }

    @Override
    public OrderPageBean getListByPage(Long userId, OrderPageBean bean) {
        OrderPageBean result = new OrderPageBean(bean.getStatus(), bean.getCurrentPage());
        Integer count = orderMapper.selectCountByUserIdAndStatus(userId, bean.getStatus());
        if (Objects.isNull(count)){
            count = 0;
        }
        bean.setTotalRows(count);
        List<Order> orders = orderMapper.selectPageByUserIdAndStatus(userId, bean);
        result.setTotalRows(count);
        result.setItems(orders);
        return result;
    }

    @Override
    @Transactional
    public void walletPay(int sumMoney,int number, Long apiId, Long voucherId, Long userId) {
        Wallet wallet = walletMapper.selectByUserId(userId);
        int walletBalance = wallet.getBalance().intValue();
        int remainBalance = walletBalance-sumMoney;
        if (voucherId!=Constants.NOT_VOUCHER){
            Voucher voucher = voucherMapper.selectByPrimaryKey(voucherId);
            if (voucher.getUserId()!=userId){
                throw new TyreException(ExceptionEnum.PARAMETER_ILLEGALITY);
            }
            remainBalance = remainBalance + voucher.getMoney().intValue();
            sumMoney = sumMoney-voucher.getMoney().intValue();
            if (remainBalance>=0){
                voucher.setStatus(Constants.VOUCHER_USED);
                voucherMapper.updateByPrimaryKeySelective(voucher);
            }
        }
        if (remainBalance<0){
            throw new TyreException(ExceptionEnum.WALLET_BALANCE_NOT_ENOUGH);
        }
        wallet.setBalance(new BigDecimal(remainBalance));
        wallet.setInvoice(wallet.getInvoice().add(new BigDecimal(sumMoney)));
        walletMapper.updateByPrimaryKeySelective(wallet);
        ApiLimitNum apiLimitNum = apiLimitNumMapper.selectByUserIdAndApiId(userId, apiId);
        if (Objects.isNull(apiLimitNum)){
            throw new TyreException(ExceptionEnum.PARAMETER_ILLEGALITY);
        }
        apiLimitNum.setLimitNum(apiLimitNum.getLimitNum()+number);
        apiLimitNumMapper.updateByPrimaryKeySelective(apiLimitNum);
        Order order = new Order();
        order.setId(Generator.getId());
        order.setStatus(Constants.PAID);
        order.setMoney(new BigDecimal(sumMoney));
        order.setPayTime(new Date());
        order.setProductId(apiId);
        order.setUserId(userId);
        order.setOrderType(number);
        orderMapper.insertSelective(order);
        Api api = apiMapper.selectByPrimaryKey(apiId);
        Message message = new Message();
        message.setUserId(wallet.getUserId());
        message.setTitle("购买"+api.getApiName()+"成功");
        message.setBody("购买了 "+api.getApiName()+number+"次数");
        message.setCategory(Constants.MSG_NOTICE);
        messageMapper.insertSelective(message);
    }

    @Override
    public void cancelOrder(Long orderId, Long userId) {
        Order order = orderMapper.selectByUserIdAndId(userId, orderId);
        if (Objects.isNull(order)){
            throw new TyreException(ExceptionEnum.PARAMETER_ILLEGALITY);
        }
        order.setStatus(Constants.NON_PAYMENT);
        orderMapper.updateStatusByPrimaryKey(order);
    }
}
