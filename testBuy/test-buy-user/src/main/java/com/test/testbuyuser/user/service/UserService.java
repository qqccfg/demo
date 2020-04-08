package com.test.testbuyuser.user.service;

import com.test.testbuyuser.user.entity.User;
import com.test.testbuyuser.user.entity.UserElement;

/**
 * @Author JackLei
 * @Date: Created in 2018/6/13 11:44
 * @Description
 */
public interface UserService {
    UserElement login(User user);

    void registry(User user) throws Exception;
}
