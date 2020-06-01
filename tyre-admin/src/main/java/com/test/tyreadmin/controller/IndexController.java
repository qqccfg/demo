package com.test.tyreadmin.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @Date: 2020/4/26 11:24
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index(){
        return "/index";
    }

    @RequestMapping("/login")
    public String login(String name, String password){
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(name, password));
            return "/index";
        }catch (AuthenticationException e){
            return "/loginError";
        }
    }

    @RequestMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }

    @RequestMapping("/goLogin")
    public String goLogin(){
        return "/login";
    }
}
