package com.test.testbuyuser.user.controller;

import com.test.testbuyuser.common.constants.Constants;
import com.test.testbuyuser.common.rese.ApiResult;
import com.test.testbuyuser.user.entity.User;
import com.test.testbuyuser.user.entity.UserElement;
import com.test.testbuyuser.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @Author JackLei
 * @Date: Created in 2018/6/12 15:46
 * @Description
 */
@RefreshScope
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;


    /**
    *@Author JackLei
    *@Date: 2018/6/13 12:07
    *@Description: 用户登录
    */
    @RequestMapping("/login")
    public ApiResult login(HttpSession session, @RequestBody @Valid User user, HttpServletResponse response){
        ApiResult rese=new ApiResult(Constants.RESP_STATUS_OK,"登录成功");
        UserElement ue=userService.login(user);
        if(ue!=null){
            //session 存redis
            //是不是已经登录了

            if (session.getAttribute(Constants.REQUEST_TOKEN_HEADER)==null){
                session.setAttribute(Constants.REQUEST_TOKEN_HEADER,ue);
            }
            String token= (String) session.getAttribute("spring:session:sessions");
            rese.setData(ue);

        }
        return rese;
    }
    /**
    *@Author JackLei
    *@Date: 2018/6/13 13:14
    *@Description: 用户注册
    */
    @RequestMapping("/registry")
    public ApiResult registry(@RequestBody @Valid User user) throws Exception {
        ApiResult rese=new ApiResult();
        userService.registry(user);
        rese.setMessage("注册成功");
        return rese;
    }
}
