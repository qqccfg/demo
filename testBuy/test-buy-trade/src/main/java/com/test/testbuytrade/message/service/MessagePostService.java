package com.test.testbuytrade.message.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;


@EnableBinding(Source.class)
    public class MessagePostService {
        @Autowired
        private Source source;

        public void postMsg(String msg){
            source.output().send(MessageBuilder.withPayload(msg).build());
        }
}
