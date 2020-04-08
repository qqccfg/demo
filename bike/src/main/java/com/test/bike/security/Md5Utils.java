package com.test.bike.security;

import org.apache.commons.codec.digest.DigestUtils;
import redis.clients.jedis.Jedis;

/**
 * @Author JackLei
 * @Date: Created in 2018/4/27 10:43
 * @Description
 */
public class Md5Utils {
    public static String getMd5(String source){
        return DigestUtils.md5Hex(source);
    }

}
