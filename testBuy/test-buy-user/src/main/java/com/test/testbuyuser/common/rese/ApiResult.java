package com.test.testbuyuser.common.rese;


import com.test.testbuyuser.common.constants.Constants;
import lombok.Data;

/**
 * @Author JackLei
 * @Date: Created in 2018/6/11 21:38
 * @Description
 */
@Data
public class ApiResult <T> {
    private int code= Constants.RESP_STATUS_OK;

    private String message;

    private T data;

    public ApiResult() {
    }

    public ApiResult(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
