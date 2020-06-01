package com.test.tyreadmin.common.emum;

import lombok.Getter;

/**
 * @Date: 2020/4/25 14:41
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Getter
public enum ExceptionEnum {

    SYS_ERROR(500, "系统异常"),
    PARAMETER_ERROR(3, "参数异常")
    ;


    private int code;

    private String message;

    ExceptionEnum(int code, String message){
        this.code = code;
        this.message = message;
    }

}
