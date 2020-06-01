package com.test.tyrelocation.common.emum;

import lombok.Getter;

/**
 * @Date: 2019/9/24 19:02
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Getter
public enum ExceptionEnum {
    NULL(1, "必须参数不能为空"),
    API_ILLEGALITY(2, "api非法"),
    PARAMETER_ILLEGALITY(3, "参数异常"),
    UNKNOWN(4, "未知"),
    ERROR_MOBILE(5, "不正确的手机号码"),
    VERIFY_IP_LIMIT(6, "此ip今天已达上限"),
    VERIFY_MOBILE_LIMIT(7, "此手机号码今天已达上限"),
    VERIFY_MOBILE_EXIST(8, "手机号码已经发送"),
    VERIFY_ERROR(9, "验证码错误"),
    USERNAME_EXIST(10, "此用户名已被注册"),
    NO_USER(11, "无此用户"),
    MOBILE_EXIST(12, "此手机号码已被注册"),
    PASSWORD_ILLEGALITY(13, "8-14字符且需包含数字,字母,特殊字符{!@#$%^&*}"),
    ADDRESS_UPPER_LIMIT(14, "地址数量已经达到了上限"),
    APPLICATION_IS_MAX(15, "应用数已达最大值"),
    OAUTH_TOKEN_ERROR(16, "获取token错误"),
    IMAGE_ERROR(17, "不正确的图片"),
    TOKEN_ERROR(18, "token错误"),
    API_NUMBER_EMPTY(19, "调用量为0"),
    WALLET_BALANCE_NOT_ENOUGH(20, "钱包余额不足"),
    API_UNABLE(21, "此API不能使用"),
    ;
    private int code;

    private String message;

    ExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
