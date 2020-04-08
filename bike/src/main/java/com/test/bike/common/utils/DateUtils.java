package com.test.bike.common.utils;

import java.util.Date;

/**
 * @Author JackLei
 * @Date: Created in 2018/5/7 21:12
 * @Description
 */
public class DateUtils {
    /**
    *@Author JackLei
    *@Date: 2018/5/7 21:12
    *@Description: 计算时间分钟
    */
    public static long getMinuts(Date start,Date end){
        return (end.getTime()-start.getTime())/(60*1000);
    }
}
