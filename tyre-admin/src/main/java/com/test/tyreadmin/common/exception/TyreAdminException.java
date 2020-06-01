package com.test.tyreadmin.common.exception;


import com.test.tyreadmin.common.emum.ExceptionEnum;
import lombok.Getter;

/**
 * @Date: 2019/9/24 18:59
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Getter
public class TyreAdminException extends RuntimeException{

    private int code;

    private String message;

    public TyreAdminException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMessage();
    }
}
