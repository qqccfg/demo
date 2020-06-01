package com.test.tyrelocation.common.emum;

import lombok.Getter;

/**
 * @Date: 2019/11/18 22:24
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Getter
public enum ConfigEnum {
    VERIFY_IP_LIMIT(10, "验证码IP的一天限制次数"),
    VERIFY_MOBILE_LIMIT(10, "验证码手机号码的一天限制次数"),
    VERIFY_MOBILE_TIMEOUT(24,"手机号码限制过期时间（单位/小时）"),
    VERIFY_IP_TIMEOUT(24,"ip限制过期时间（单位/小时）"),
    VERIFY_CODE_TIMEOUT(60, "验证码限制过期时间（单位/秒）"),
    RETRIEVE_TIMEOUT(10, "找回密码的过期时间（单位/分钟）"),
    ORDER_EXPIRE(1800000,"30分钟"),
    APPLICATION_NUM(10, "应用最大数"),
    TOKEN_TIMEOUT(10, "限制时间（单位/分钟）")
    ;

    private int code;

    private String message;

    ConfigEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
