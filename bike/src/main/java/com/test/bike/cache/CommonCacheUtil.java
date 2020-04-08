package com.test.bike.cache;

import com.test.bike.common.exception.TestBikeException;
import com.test.bike.user.entity.UserElement;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.io.ResolverUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.Map;

/**
 * @Author JackLei
 * @Date: Created in 2018/4/26 21:34
 * @Description
 */
@Component
@Slf4j
public class CommonCacheUtil {

    private static final String TOKEN_PREFIX = "token.";

    private static final String USER_PREFIX = "user.";

    public static final int STALE_TIME=2592000;



    @Autowired
    private JedisPoolWrapper jedisPoolWrapper;

    /**
    *@Author JackLei
    *@Date: 2018/4/26 21:45
    *@Description: 缓存  永久的value
    */
    public void cache(String key,String value){
        try {
            JedisPool jedisPool=jedisPoolWrapper.getJedisPool();
            if (jedisPool!=null){
                try(Jedis jedis=jedisPool.getResource()){
                    jedis.select(0);
                    jedis.set(key,value);
                }
            }
        }catch (Exception e){
            log.error("fail to cache value",e);
        }
    }
    /**
    *@Author JackLei
    *@Date: 2018/4/26 22:16
    *@Description: 获取缓存 key
    */
    public String getCacheValue(String key){
        String value=null;
        try {
            JedisPool jedisPool=jedisPoolWrapper.getJedisPool();
            if (jedisPool!=null){
                try(Jedis jedis=jedisPool.getResource()){
                    jedis.select(0);
                    value=jedis.get(key);
                }
            }
        }catch (Exception e){
            log.error("fail to getCacheValue",e);
        }
        return value;
    }
    /**
    *@Author JackLei
    *@Date: 2018/4/26 22:21
    *@Description: 设置有期的 key value
    */
    public long cacheNXExpire(String key,String value,int expire){
        long result=0;
        try{
            JedisPool jedisPool=jedisPoolWrapper.getJedisPool();
                if (jedisPool!=null){
                    try(Jedis jedis=jedisPool.getResource()){
                      jedis.select(0);
                      result=jedis.setnx(key,value);
                      jedis.expire(key,expire);
                    }
                }
        }catch (Exception e){
            log.error("fail to cacheNXExpire",e);
        }
        return result;

    }
    /**
    *@Author JackLei
    *@Date: 2018/4/26 22:33
    *@Description: 删除 缓存
    */
    public void delCache(String key){
        try{
            JedisPool jedisPool=jedisPoolWrapper.getJedisPool();
                if (jedisPool!=null){
                    try(Jedis jedis=jedisPool.getResource()){
                        jedis.select(0);
                        jedis.del(key);
                    }
                }
        }catch (Exception e){
            log.error("fail to delCache",e);
        }
    }
    /**
    *@Author JackLei
    *@Date: 2018/4/26 22:38
    *@Description: 登录时设置 token
    */
    public void putTokenWhenLogin(UserElement ue){
        try{
            JedisPool pool=jedisPoolWrapper.getJedisPool();
                if (pool!=null){
                    try(Jedis jedis=pool.getResource()){
                        jedis.select(0);
                        Transaction transaction=jedis.multi();
                        try{
                            transaction.del(TOKEN_PREFIX+ue.getToken());
                            transaction.hmset(TOKEN_PREFIX+ue.getToken(),ue.toMap());
                            transaction.expire(TOKEN_PREFIX+ue.getToken(),STALE_TIME);
                            transaction.sadd(USER_PREFIX+ue.getUserId(),ue.getToken());
                            transaction.exec();
                        }catch (Exception e){
                            transaction.discard();
                            log.error("fail to Transaction",e);
                        }
                    }

                }
        }catch (Exception e){
            log.error("fail to cache token redis",e);
        }

    }
    /**
    *@Author JackLei
    *@Date: 2018/4/28 23:17
    *@Description: 根据token获取用户信息
    */
    public UserElement getUserByToken(String token) throws TestBikeException {
        UserElement ue=null;
        JedisPool jedisPool=jedisPoolWrapper.getJedisPool();
        try(Jedis jedis=jedisPool.getResource()){
            try{
                jedis.select(0);
                Map<String,String> map=jedis.hgetAll(TOKEN_PREFIX+token);
                if (map!=null&& !map.isEmpty()){
                    ue=UserElement.fromMap(map);
                }else {

                    log.warn("fial to user by token",token);
                }
            }catch (Exception e){
                log.warn("fial to user by token",e);
                throw new TestBikeException("fial to user by token");
            }
        }
        return ue;
    }
    /**
    *@Author JackLei
    *@Date: 2018/4/30 22:06
    *@Description: 短息验证储存
     * 1 当前验证码未过期   2 手机号超过当日验证码次数上限  3 ip超过当日验证码次数上线
    */
    public int putVercode(String key, String value, String type, int timeout, String ip){
        try {
            JedisPool jedisPool=jedisPoolWrapper.getJedisPool();
            try(Jedis jedis=jedisPool.getResource()){
                jedis.select(0);
                if (StringUtils.isBlank(ip)){
                    return 3;
                }
                String ipKey="ip."+ip;
                String ipSendCount=jedis.get(ipKey);
                try{
                    if(ipSendCount!=null&&Integer.parseInt(ipSendCount)>=10){
                        return 3;
                    }
                }catch (NumberFormatException e){
                    log.error("Fail to process ip send count", e);
                }
                String sendCount = jedis.get(key + "." + type);
                try{
                    if(sendCount!=null&&Integer.parseInt(sendCount)>=10){
                        jedis.del(key);
                        return 2;
                    }
                }catch (NumberFormatException e){
                    log.error("fail to send count",e);
                }
                long massage=jedis.setnx(key,value);
                if(massage==1){
                    return 1;
                }
                try{
                    jedis.expire(key,timeout);
                    long val=jedis.incr(key+"."+type);
                    if(val==1){
                        jedis.expire(key + "." + type, 86400);
                    }
                    long val2=jedis.incr(ipKey);
                    if (val2==1){
                        jedis.expire(ipKey,86400);
                    }
                }catch (Exception e){
                    log.error("fail to cache data",e);
                }

            }
        }catch (Exception e){
            log.error("fail to putVercode",e);
        }
        return 0;
    }
}
