package com.test.bike.sms;

/**
 * @Author JackLei
 * @Date: Created in 2018/4/30 23:54
 * @Description
 */
public interface SmsSend {
    void sendSms(String phone,String tplId,String params);
}
