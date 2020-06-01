package com.test.tyreadmin.service;

import com.test.tyreadmin.common.constant.Constants;
import com.test.tyreadmin.common.emum.ExceptionEnum;
import com.test.tyreadmin.common.exception.TyreAdminException;
import com.test.tyreadmin.common.page.PageQueryBean;
import com.test.tyreadmin.entity.ApiLimitNum;
import com.test.tyreadmin.entity.Order;
import com.test.tyreadmin.entity.PayRecord;
import com.test.tyreadmin.entity.User;
import com.test.tyreadmin.repository.ApiLimitNumMapper;
import com.test.tyreadmin.repository.OrderMapper;
import com.test.tyreadmin.repository.PayRecordMapper;
import com.test.tyreadmin.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Date: 2020/4/22 19:02
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PayRecordMapper payRecordMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ApiLimitNumMapper apiLimitNumMapper;

    @Override
    public List<User> getUsers() {
        List<User> users = userMapper.selectAll();
        return users;
    }

    @Override
    public void freezeUser(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (Objects.isNull(user)||user.getStatus()==Constants.USER_FREEZE_STATUS){
            return;
        }
        userMapper.updateStatusById(id, Constants.USER_FREEZE_STATUS);
    }



    @Override
    public void unfreezeUser(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (Objects.isNull(user)||user.getStatus()==Constants.USER_USABLE_STATUS){
            return;
        }
        userMapper.updateStatusById(id, Constants.USER_USABLE_STATUS);
    }

    @Override
    public User getUserByMobile(String mobile) {
        return userMapper.selectByMobile(mobile);
    }

    @Override
    public PageQueryBean getPayRecordList(Long userId, PageQueryBean pageQueryBean) {
        Integer count =  payRecordMapper.selectCountByUserId(userId);
        if (Objects.isNull(count)){
            count = 0;
        }
        pageQueryBean.setTotalRows(count);
        List<PayRecord> payRecords = payRecordMapper.selectPageByUserId(userId, pageQueryBean);
        pageQueryBean.setItems(payRecords);
        return pageQueryBean;
    }

    @Override
    public PageQueryBean getOrderList(Long userId, PageQueryBean pageQueryBean) {
        Integer count = orderMapper.selectCountByUserId(userId);
        if (Objects.isNull(count)){
            count = 0;
        }
        pageQueryBean.setTotalRows(count);
        List<Order> orders = orderMapper.selectPageByUserId(userId, pageQueryBean);
        pageQueryBean.setItems(orders);
        return pageQueryBean;
    }

    @Override
    public PageQueryBean apiNumList(Long userId, PageQueryBean pageQueryBean) {
        Integer count = apiLimitNumMapper.selectCountByUserId(userId);
        if (Objects.isNull(count)){
            count = 0;
        }
        pageQueryBean.setTotalRows(count);
        List<ApiLimitNum> apiLimitNums= apiLimitNumMapper.selectPageByUserId(userId, pageQueryBean);
        pageQueryBean.setItems(apiLimitNums);
        return pageQueryBean;
    }

    @Override
    public void updateApiNum(long id, int val) {
        ApiLimitNum apiLimitNum = apiLimitNumMapper.selectByPrimaryKey(id);
        if (Objects.isNull(apiLimitNum)){
            throw new TyreAdminException(ExceptionEnum.PARAMETER_ERROR);
        }
        apiLimitNum.setLimitNum(val);
        apiLimitNumMapper.updateByPrimaryKeySelective(apiLimitNum);
    }
}
