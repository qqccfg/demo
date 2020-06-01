package com.test.tyrelocation.controller;

import com.test.tyrelocation.common.Exception.TyreException;
import com.test.tyrelocation.common.emum.ExceptionEnum;
import com.test.tyrelocation.entity.User;
import org.apache.shiro.SecurityUtils;

/**
 * @Date: 2019/11/29 19:31
 * @Author: JackLei
 * @Description:
 * @Version:
 */
public class BaseController {
    public Long getUserId(){
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("userInfo");
        if (user==null){
            throw new TyreException(ExceptionEnum.UNKNOWN);
        }
        return user.getId();
    }
    public String getUsername(){
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("userInfo");
        if (user==null){
            throw new TyreException(ExceptionEnum.UNKNOWN);
        }
        return user.getUsername();
    }
}
