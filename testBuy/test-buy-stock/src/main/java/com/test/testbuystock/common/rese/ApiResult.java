package com.test.testbuystock.common.rese;

import com.test.testbuystock.common.constants.Constants;
import lombok.Data;

/**
 * @author JackLei
 * @Title: ApiResult
 * @ProjectName testBuy
 * @Description:
 * @date 2018/6/2117:18
 **/
@Data
public class ApiResult <T>{
    private int statusCode=Constants.RESP_STATUS_OK;

    private String message;

    private T data;

    public ApiResult() {
    }

    public ApiResult(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
