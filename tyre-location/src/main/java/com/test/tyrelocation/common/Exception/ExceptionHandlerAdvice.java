package com.test.tyrelocation.common.Exception;

import com.test.tyrelocation.common.emum.ExceptionEnum;
import com.test.tyrelocation.common.emum.ResponseEnum;
import com.test.tyrelocation.common.response.ResponseRet;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Date: 2019/9/24 20:59
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@ControllerAdvice
@ResponseBody
@Log4j2
public class ExceptionHandlerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseRet handlerException(Exception e){
        log.error(e.getMessage(), e);
        return new ResponseRet(ResponseEnum.SYS_ERROR);
    }

    @ExceptionHandler(TyreException.class)
    public ResponseRet handlerException(TyreException e){
        log.error(e.getMessage(), e);
        return new ResponseRet(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseRet handlerException(MethodArgumentNotValidException e){
        List<ObjectError> list =e.getBindingResult().getAllErrors();
        String result="参数不合法";
        if (list.size()>0){
            result=list.get(0).getDefaultMessage();
        }
        log.error("参数不合法", e);
        return new ResponseRet(HttpStatus.BAD_REQUEST.value(),result);
    }

}
