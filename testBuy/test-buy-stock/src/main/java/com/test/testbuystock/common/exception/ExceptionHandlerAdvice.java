package com.test.testbuystock.common.exception;

import com.test.testbuystock.common.constants.Constants;
import com.test.testbuystock.common.rese.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author JackLei
 * @Title: ExceptionHandlerAdvice
 * @ProjectName testBuy
 * @Description:
 * @date 2018/6/2117:15
 **/
@ControllerAdvice
@ResponseBody
@Slf4j
public class ExceptionHandlerAdvice {
    @ExceptionHandler(Exception.class)
    public ApiResult handlerException(Exception e){
        log.error(e.getMessage(),e);
        return new ApiResult(Constants.RESP_STATUS_INTERNAL_ERROR,"系统异常，稍后再试");
    }
    public ApiResult handlerException(TestbuyException e){
        log.error(e.getMessage(),e);
        return new ApiResult(e.getStatusCode(),e.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult handlerIllegalParamException(MethodArgumentNotValidException e){
        log.error(e.getMessage(),e);
        List<ObjectError> errors=e.getBindingResult().getAllErrors();
        String message="参数不合法";
        if(errors.size()>0){
            message=errors.get(0).getDefaultMessage();
        }
        return new ApiResult(Constants.RESP_STATUS_BADREQUEST,message);
    }
}
