package com.test.bike.jms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.test.bike.sms.SmsSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;

/**
 * @Author JackLei
 * @Date: Created in 2018/5/1 11:10
 * @Description
 */
@Component(value = "smsProcessor")
public class SmsProcess {
    @Autowired
    private JmsMessagingTemplate messagingTemplate;

    @Autowired
    private SmsSend smsSend;

    public void SmsSendtoQueue(Destination destination,final String message){
        messagingTemplate.convertAndSend(destination,message);
    }
    @JmsListener(destination = "sms.queue")
    public void doSendSmsMassage(String text){
        JSONObject jsonObject= JSON.parseObject(text);
        smsSend.sendSms(jsonObject.getString("mobile"),jsonObject.getString("tplId"),jsonObject.getString("vercode"));

    }
}
