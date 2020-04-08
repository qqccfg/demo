package com.test.testbuytrade.common.exception;


import com.test.testbuyuser.common.constants.Constants;
import com.test.testbuyuser.common.rese.ApiResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author JackLei
 * @Date: Created in 2018/6/11 21:35
 * @Description
 */
@ControllerAdvice
@ResponseBody
public class ExceptionHandlerAdvice {
    @ExceptionHandler(Exception.class)
    public ApiResult handerException(){
        return new ApiResult(Constants.RESP_STATUS_INTERNAL_ERROR,"系统内部错误");
    }

    @ExceptionHandler(TestBuyException.class)
    public ApiResult handerException(TestBuyException e){
        return new ApiResult(e.getStatusCode(),e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult handerException(MethodArgumentNotValidException e){
        List<ObjectError> errors=e.getBindingResult().getAllErrors();
        String message = "参数不合法";
        if(errors.size()>0){
            message=errors.get(0).getDefaultMessage();
        }
        return new ApiResult(Constants.RESP_STATUS_BADREQUEST,message);
    }
}
