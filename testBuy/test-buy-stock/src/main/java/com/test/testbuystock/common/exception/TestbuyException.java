package com.test.testbuystock.common.exception;

import com.test.testbuystock.common.constants.Constants;
import lombok.Data;

/**
 * @author JackLei
 * @Title: TestbuyException
 * @ProjectName testBuy
 * @Description:
 * @date 2018/6/2117:09
 **/

public class TestbuyException extends RuntimeException {
    private int statusCode=Constants.RESP_STATUS_OK;

    public TestbuyException(String message) {
        super(message);
    }

    public TestbuyException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
