package com.test.tyrelocation.common.Exception;

import com.test.tyrelocation.common.emum.ExceptionEnum;
import lombok.Getter;

/**
 * @Date: 2019/9/24 18:59
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Getter
public class TyreException extends RuntimeException{

    private int code;

    private String message;

    public TyreException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMessage();
    }
}
