package com.test.tyrelocation.common.tool;

import com.test.tyrelocation.common.emum.ConfigEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Date: 2019/11/20 15:55
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * @Description： 存储验证码
     * @Return 返回 0：成功  1：IP达到上限  2：手机号码达到上限  3：手机号码还没过期
     * @Date 2019/11/20 16:03
     */
    public int saveVerifyCode(String mobile, String type, String ip, String value){
        String mobileKey = mobile+type;
        String mobileCountKey = mobile+type+"_count";
        String ipKey = ip+"_count";
        String ipCount = redisTemplate.opsForValue().get(ipKey);
        if (ipCount!=null &&
                Integer.parseInt(ipCount)>= ConfigEnum.VERIFY_IP_LIMIT.getCode()){
            return 1;
        }
        String mobileCount = redisTemplate.opsForValue().get(mobileCountKey);
        if (mobileCount!=null &&
                Integer.parseInt(mobileCount)>=ConfigEnum.VERIFY_MOBILE_LIMIT.getCode()){
            return 2;
        }
        Boolean isCode = redisTemplate.opsForValue().setIfAbsent(mobileKey, value, ConfigEnum.VERIFY_CODE_TIMEOUT.getCode(), TimeUnit.SECONDS);
        if (isCode){
            Long ipIncr = redisTemplate.opsForValue().increment(ipKey);
            if (ipIncr==1){
                redisTemplate.boundSetOps(ipKey).expire(ConfigEnum.VERIFY_IP_TIMEOUT.getCode(), TimeUnit.HOURS);
            }
            Long mobileIncr = redisTemplate.opsForValue().increment(mobileCountKey);
            if (mobileIncr==1){
                redisTemplate.boundSetOps(mobileCountKey).expire(ConfigEnum.VERIFY_MOBILE_TIMEOUT.getCode(), TimeUnit.HOURS);
            }
        }else {
            return 3;
        }
        return 0;
    }

    public String saveOauthToken(String userId){
        String token = Generator.getToken();
        redisTemplate.opsForValue().set(token, userId, ConfigEnum.TOKEN_TIMEOUT.getCode(), TimeUnit.MINUTES);
        return token;
    }
}
