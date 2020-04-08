package com.test.bike.cache;

import com.test.bike.common.constants.Parameters;
import com.test.bike.common.exception.TestBikeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;

/**
 * @Author JackLei
 * @Date: Created in 2018/4/26 21:50
 * @Description 获取jedispool
 */
@Component
@Slf4j
public class JedisPoolWrapper {

    public static final int WAITING_TIME=2000;

    @Autowired
    private Parameters parameters;

    private JedisPool jedisPool = null;
    @PostConstruct
    public void init() throws TestBikeException {
        try {
            JedisPoolConfig config=new JedisPoolConfig();
            config.setMaxTotal(parameters.getRedisMaxTotal());
            config.setMaxIdle(parameters.getRedisMaxIdle());
            config.setMaxWaitMillis(parameters.getRedisMaxWaitMillis());
            jedisPool=new JedisPool(config,parameters.getRedisHost(),parameters.getRedisPort(),WAITING_TIME);


        }catch (Exception e){
            log.error("初始化jedis poll失败",e);
            throw new TestBikeException("初始化jedis poll失败");
        }
    }
    public JedisPool getJedisPool(){
        return jedisPool;
    }

}
