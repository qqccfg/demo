package com.test.tyreadmin.service;

import com.test.tyreadmin.common.page.PageQueryBean;
import com.test.tyreadmin.entity.PayRecord;
import com.test.tyreadmin.entity.User;

import java.util.List;

/**
 * @Date: 2020/4/22 19:01
 * @Author: JackLei
 * @Description:
 * @Version:
 */
public interface UserService {
    List<User> getUsers();

    void freezeUser(Long id);

    void unfreezeUser(Long id);

    User getUserByMobile(String mobile);

    PageQueryBean getPayRecordList(Long userId, PageQueryBean pageQueryBean);

    PageQueryBean getOrderList(Long userId, PageQueryBean pageQueryBean);

    PageQueryBean apiNumList(Long userId, PageQueryBean pageQueryBean);

    void updateApiNum(long id, int val);
}
