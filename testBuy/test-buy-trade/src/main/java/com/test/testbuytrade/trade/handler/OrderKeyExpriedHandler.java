package com.test.testbuytrade.trade.handler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * @author JackLei
 * @Title: OrderKeyExpriedHandler
 * @ProjectName testBuy
 * @Description:
 * @date 2018/6/2917:06
 **/
@Configuration
public class OrderKeyExpriedHandler {
    @Bean
   public RedisMessageListenerContainer configRedisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory){
       RedisMessageListenerContainer listenerContainer=new RedisMessageListenerContainer();
       listenerContainer.setConnectionFactory(redisConnectionFactory);
       listenerContainer.addMessageListener((message, listener) -> {
           System.out.println("redis过期事件"+new String(message.getBody()));
       },new PatternTopic("__keyevent@*__:expired"));
       return listenerContainer;
   }
}
