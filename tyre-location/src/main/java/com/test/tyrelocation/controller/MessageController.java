package com.test.tyrelocation.controller;

import com.test.tyrelocation.common.emum.ResponseEnum;
import com.test.tyrelocation.common.page.CategoryQueryBean;
import com.test.tyrelocation.common.page.PageQueryBean;
import com.test.tyrelocation.common.response.ResponseRet;
import com.test.tyrelocation.common.tool.ConversionUtils;
import com.test.tyrelocation.entity.Message;
import com.test.tyrelocation.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Date: 2019/12/3 19:39
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Controller
@RequestMapping("/message")
public class MessageController extends BaseController{

    @Autowired
    private MessageService messageService;


    @RequestMapping("")
    public String getMessages(@RequestParam(name = "page", defaultValue = "1") int page,
                              @RequestParam(name = "category", defaultValue = "0")int category,
                              @RequestParam(name = "status", defaultValue = "1")int status, Model model){
        CategoryQueryBean queryBean = new CategoryQueryBean();
        queryBean.setCurrentPage(page);
        queryBean.setCategory(category);
        queryBean.setStatus(status);
        PageQueryBean result = messageService.getMessages(getUserId(), queryBean);
        List<String> msgTypes = new ArrayList<>();
        for (Message message : (List<Message>)result.getItems()){
            String val = ConversionUtils.msgTypeToString(message.getCategory());
            msgTypes.add(val);
        }
        model.addAttribute("body", "msgList");
        model.addAttribute("category", category);
        model.addAttribute("status", status);
        model.addAttribute("msgType", msgTypes);
        model.addAttribute("queryBean", result);
        return "user/message";
    }

    @PostMapping("/delete")
    @ResponseBody
    public ResponseRet delete(@RequestBody List<Long> ids){
        messageService.delete(getUserId(), ids);
        return new ResponseRet(ResponseEnum.OK);
    }

    @PostMapping("/read")
    @ResponseBody
    public ResponseRet read(@RequestBody List<Long> ids){
        messageService.read(getUserId(), ids);
        return new ResponseRet(ResponseEnum.OK);
    }

    @RequestMapping("/detail")
    public String detail(@RequestParam("id") Long id,
                         @RequestParam(value ="category", defaultValue = "0") int category,
                         @RequestParam(value ="status", defaultValue = "1") int status ,
                         @RequestParam(value ="page", defaultValue = "1") int page, Model model){
        List<Message> result = messageService.detail(getUserId(), id);
        model.addAttribute("body", "msgDetail");
        model.addAttribute("msgs", result);
        model.addAttribute("category", category);
        model.addAttribute("status", status);
        model.addAttribute("page", page);
        return "user/message";
    }


}
