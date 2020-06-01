package com.test.tyrelocation.controller;


import com.test.tyrelocation.common.Exception.TyreException;
import com.test.tyrelocation.common.constant.Constants;
import com.test.tyrelocation.common.emum.ExceptionEnum;
import com.test.tyrelocation.common.emum.ResponseEnum;
import com.test.tyrelocation.common.response.ResponseRet;
import com.test.tyrelocation.common.tool.HttpUtils;
import com.test.tyrelocation.entity.UserElement;
import com.test.tyrelocation.service.UserService;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;


/**
 * @Date: 2019/11/2 18:47
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Controller
@RequestMapping("/user")
@Log4j2
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    @ResponseBody
    public ResponseRet login(@RequestParam("username") String username, @RequestParam("password") String password){
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            throw new TyreException(ExceptionEnum.PARAMETER_ILLEGALITY);
        }
        AuthenticationToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        }catch (AuthenticationException e){
            log.warn("登陆失败："+username);
            return new ResponseRet(ResponseEnum.LOGIN_FAIL);
        }

        return new ResponseRet(ResponseEnum.OK);
    }

    @RequestMapping("/gologin")
    public String goLogin(){
        return "login";
    }

    @GetMapping("/verification")
    @ResponseBody
    public ResponseRet sendVerification(@RequestParam("mobile") String mobile, HttpServletRequest request){
        if(!HttpUtils.isMobile(mobile)){
            throw new TyreException(ExceptionEnum.ERROR_MOBILE);
        }
        String ip = HttpUtils.getIpForRequest(request);
        userService.sendVerification(mobile, Constants.VERIFY_CODE_REG,ip);
        return new ResponseRet(ResponseEnum.OK);
    }

    @GetMapping("/retVerification")
    @ResponseBody
    public ResponseRet sendRetVerification(@RequestParam("key") String key, HttpServletRequest request){
        if(StringUtils.isEmpty(key)){
            throw new TyreException(ExceptionEnum.ERROR_MOBILE);
        }
        String ip = HttpUtils.getIpForRequest(request);
        userService.sendRetVerification(key, Constants.VERIFY_CODE_RET,ip);
        return new ResponseRet(ResponseEnum.OK);
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseRet register(@Valid @RequestBody UserElement userElement){
        userService.register(userElement);
        return new  ResponseRet(ResponseEnum.OK);
    }

    @RequestMapping("/retrieve1")
    @ResponseBody
    public ResponseRet<Map<String, String>> toMobile(@RequestParam("usmMob") String usmMob){
        if (StringUtils.isEmpty(usmMob)){
            throw new TyreException(ExceptionEnum.NULL);
        }
        Map<String, String> data = userService.toMobile(usmMob);
        return new ResponseRet<>(ResponseEnum.OK, data);
    }

    @RequestMapping("/retrieve2")
    @ResponseBody
    public ResponseRet<String> Retrieve(@RequestParam("key") String key, @RequestParam("verifyCode") String verifyCode, Model model){
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(verifyCode)){
            throw new TyreException(ExceptionEnum.NULL);
        }
        String data = userService.isRetVerifyCode(key, verifyCode);
        return new ResponseRet<>(ResponseEnum.OK, data);
    }

    @GetMapping("/goRetrieve")
    public String goRetrieve(@RequestParam("data") String data, Model model){
        String value = userService.callbackCheck(data);
        model.addAttribute("key", data);
        model.addAttribute("username", value);
        return "/user/retrieve";
    }

    @RequestMapping("/resetPassword")
    @ResponseBody
    public ResponseRet resetPassword(@RequestParam("key") String key, @RequestParam("password") String password){
        if (StringUtils.isEmpty(key) || !HttpUtils.checkPassword(password)){
            throw new TyreException(ExceptionEnum.PASSWORD_ILLEGALITY);
        }
        userService.resetPassword(key, password);
        return new ResponseRet(ResponseEnum.OK);
    }

    @RequestMapping("/success")
    public String success(Model model){
        model.addAttribute("url", "/user/gologin");
        return "/common/success";
    }

    @RequestMapping("/logout")
    public String logout(){
        SecurityUtils.getSubject().logout();
        return "login";
    }
}
