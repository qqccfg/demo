package com.test.tyreadmin.common.emum;

import lombok.Getter;

/**
 * @Date: 2020/4/24 17:00
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Getter
public enum ResponseEnum {

    OK(200, "成功"),
    SYS_ERROR(500, "系统异常")
    ;

    private int code;

    private String message;

    ResponseEnum(int code, String message){
        this.code = code;
        this.message = message;
    }
}
