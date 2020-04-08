package com.test.testbuytrade.common.exception;

/**
 * @Author JackLei
 * @Date: Created in 2018/6/11 21:23
 * @Description
 */

public class TestBuyException extends RuntimeException{
   private int statusCode;

    public TestBuyException(int statusCode,String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public TestBuyException(String message) {
        super(message);
    }

    public int getStatusCode(){
        return statusCode;
    }
}
