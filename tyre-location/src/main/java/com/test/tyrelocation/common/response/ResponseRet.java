package com.test.tyrelocation.common.response;


import com.test.tyrelocation.common.emum.ResponseEnum;
import lombok.Data;

/**
 * @Date: 2019/9/24 18:41
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Data
public class ResponseRet<T> {

    private int code;

    private String message;

    private T data;


    public ResponseRet(ResponseEnum responseEnum, T data) {
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
        this.data = data;
    }

    public ResponseRet(ResponseEnum responseEnum) {
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
    }

    public ResponseRet(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
