package com.test.tyreadmin.controller;

import com.test.tyreadmin.common.emum.ResponseEnum;
import com.test.tyreadmin.common.page.PageQueryBean;
import com.test.tyreadmin.common.response.ResponseRet;
import com.test.tyreadmin.entity.User;
import com.test.tyreadmin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Date: 2020/4/22 18:41
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Controller
@RequestMapping("user")
public class UserController {


    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    public String index(){
        return "/user/index";
    }

    @RequestMapping("/info")
    public String userInfo(String mobile, Model model){
        User user = userService.getUserByMobile(mobile);
        model.addAttribute("user", user);
        return "/user/info";
    }

    @RequestMapping("/freeze")
    public String freeze(String mobile, Long userId, Model model){
        userService.freezeUser(userId);
        User user = userService.getUserByMobile(mobile);
        model.addAttribute("user", user);
        return "/user/info";
    }

    @RequestMapping("/unFreeze")
    public String unFreeze(String mobile, Long userId, Model model){
        userService.unfreezeUser(userId);
        User user = userService.getUserByMobile(mobile);
        model.addAttribute("user", user);
        return "/user/info";
    }

    @RequestMapping("/topUpInfo")
    public String topUpInfo(@RequestParam(value = "page", defaultValue = "1") int page,
                            String mobile, Long userId, Model model){
        PageQueryBean pageQueryBean = new PageQueryBean();
        pageQueryBean.setCurrentPage(page);
        PageQueryBean result = userService.getPayRecordList(userId, pageQueryBean);
        model.addAttribute("result", result);
        model.addAttribute("mobile", mobile);
        return "/user/payRecord";

    }

    @RequestMapping("/order")
    public String orderInfo(@RequestParam(value = "page", defaultValue = "1") int page, Long userId,
                            String mobile, Model model){
        PageQueryBean pageQueryBean = new PageQueryBean();
        pageQueryBean.setCurrentPage(page);
        PageQueryBean result = userService.getOrderList(userId, pageQueryBean);
        model.addAttribute("result", result);
        model.addAttribute("mobile", mobile);
        return "/user/order";
    }

    @RequestMapping("/apiNumList")
    public String apiNumList(@RequestParam(value = "page", defaultValue = "1") int page, Long userId,
                             String mobile, Model model){
        PageQueryBean pageQueryBean = new PageQueryBean();
        pageQueryBean.setCurrentPage(page);
        PageQueryBean result = userService.apiNumList(userId ,pageQueryBean);
        model.addAttribute("result", result);
        model.addAttribute("mobile", mobile);
        return "/user/apiNumList";
    }

    @RequestMapping("/updateApiNum")
    @ResponseBody
    public ResponseRet updateApiNum(long id, int val){
        userService.updateApiNum(id, val);
        return new ResponseRet(ResponseEnum.OK);
    }

}
