package com.test.tyreadmin.common.exception;

import com.test.tyreadmin.common.emum.ResponseEnum;
import com.test.tyreadmin.common.response.ResponseRet;
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
public class ExceptionHandlerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseRet handlerException(Exception e){
        return new ResponseRet(ResponseEnum.SYS_ERROR);
    }

    @ExceptionHandler(TyreAdminException.class)
    public ResponseRet handlerException(TyreAdminException e){
        return new ResponseRet(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseRet handlerException(MethodArgumentNotValidException e){
        List<ObjectError> list =e.getBindingResult().getAllErrors();
        String result="参数不合法";
        if (list.size()>0){
            result=list.get(0).getDefaultMessage();
        }
        return new ResponseRet(HttpStatus.BAD_REQUEST.value(),result);
    }

}
