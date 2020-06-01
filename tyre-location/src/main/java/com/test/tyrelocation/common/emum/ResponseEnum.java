package com.test.tyrelocation.common.emum;

import lombok.Getter;

/**
 * @Date: 2019/9/24 18:48
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Getter
public enum ResponseEnum {

    SYS_ERROR(500,"系统错误"),
    OK(200, "成功"),
    LOGIN_FAIL(22, "登陆失败")

    ;
    private int code;

    private String message;

    ResponseEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
