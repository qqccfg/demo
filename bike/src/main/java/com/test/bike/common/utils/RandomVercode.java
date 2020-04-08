package com.test.bike.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Random;

/**
 * @Author JackLei
 * @Date: Created in 2018/4/30 22:44
 * @Description 随机生成验证码
 */
public class RandomVercode {
    /**
    *@Author JackLei
    *@Date: 2018/5/7 19:27
    *@Description: 生成随机验证码
    */
    public static String getLoginVercode(){
        Random random=new Random();
        return StringUtils.substring(String.valueOf(random.nextInt()),2,6);
    }
    /**
    *@Author JackLei
    *@Date: 2018/5/7 19:28
    *@Description: 生成订单随机
    */
    public static String getRecordValue(){
        Random random=new Random();
        return String.valueOf(Math.abs(random.nextInt()));
    }

    public static void main(String[] args) {
        System.out.println(getRecordValue());
    }

}
