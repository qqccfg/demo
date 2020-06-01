package com.test.tyreadmin.controller;

import com.test.tyreadmin.common.emum.ResponseEnum;
import com.test.tyreadmin.common.page.PageQueryBean;
import com.test.tyreadmin.common.response.ResponseRet;
import com.test.tyreadmin.entity.Message;
import com.test.tyreadmin.entity.SysMessage;
import com.test.tyreadmin.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Date: 2020/4/25 16:51
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@RequestMapping("message")
@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @RequestMapping("/index")
    public String index(){
        return "/message/index";
    }

    @RequestMapping("/sendSys")
    @ResponseBody
    public ResponseRet sendSysMessage(@RequestBody SysMessage sysMessage){
        messageService.sendSysMessage(sysMessage);
        return new ResponseRet(ResponseEnum.OK);
    }

    @RequestMapping("/sendUser")
    @ResponseBody
    public ResponseRet sendUserMessage(@RequestBody Message message){
        messageService.sendMessage(message);
        return new ResponseRet(ResponseEnum.OK);
    }

    @RequestMapping("/sysMsgList")
    public String sysMsgList(@RequestParam(value = "page",defaultValue = "1") int page, Model model){
        PageQueryBean pageQueryBean = new PageQueryBean();
        pageQueryBean.setCurrentPage(page);
        PageQueryBean result = messageService.sysMessageList(pageQueryBean);
        model.addAttribute("result", result);
        return "/message/sysMessageList";
    }

    @RequestMapping("/deleteSysMsg")
    public String deleteSysMsg(int page, Long id, Model model){
        messageService.deleteSysMessage(id);
        PageQueryBean pageQueryBean = new PageQueryBean();
        pageQueryBean.setCurrentPage(page);
        PageQueryBean result = messageService.sysMessageList(pageQueryBean);
        model.addAttribute("result", result);
        return "/message/sysMessageList";
    }

    @RequestMapping("/sysMsgInfo")
    public String sysMsgInfo(Long id, Model model){
        SysMessage sysMessage = messageService.sysMsgInfo(id);
        model.addAttribute("sysMessage", sysMessage);
        return "/message/sysMsgInfo";
    }
}
