package com.test.bike.common.exception;

import com.test.bike.common.constants.Constants;

/**
 * @Author JackLei
 * @Date: Created in 2018/4/26 20:17
 * @Description 自定义的错误
 */
public class TestBikeException extends Exception {

    public TestBikeException(String message){
        super(message);
    }

    public int getCode(){return Constants.RESP_STATUS_INTERNAL_ERROR;}
}
