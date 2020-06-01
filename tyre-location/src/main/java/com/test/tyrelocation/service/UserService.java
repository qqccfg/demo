package com.test.tyrelocation.service;

import com.test.tyrelocation.entity.User;
import com.test.tyrelocation.entity.UserElement;

import java.util.Map;

/**
 * @Date: 2019/11/11 14:08
 * @Author: JackLei
 * @Description:
 * @Version:
 */
public interface UserService {
    User queryByUsernameOrMobile(String username);

    void sendVerification(String mobile,String type ,String ip);

    void register(UserElement userElement);

    Map<String, String> toMobile(String usmMob);

    void sendRetVerification(String key, String type, String ip);

    String isRetVerifyCode(String key, String verifyCode);

    void resetPassword(String key, String password);

    String callbackCheck(String data);
}
