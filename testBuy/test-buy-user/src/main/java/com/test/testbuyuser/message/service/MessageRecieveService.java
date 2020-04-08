package com.test.testbuyuser.message.service;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;


@EnableBinding(Sink.class)
public class MessageRecieveService {

    @StreamListener(Sink.INPUT)
    public void recieveMsg(Object payload){
        System.out.println(payload);
    }
}
