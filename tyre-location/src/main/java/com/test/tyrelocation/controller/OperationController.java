package com.test.tyrelocation.controller;

import com.test.tyrelocation.common.response.ResponseRet;
import com.test.tyrelocation.entity.Image;
import com.test.tyrelocation.entity.SysMessage;
import com.test.tyrelocation.entity.User;
import com.test.tyrelocation.entity.Wallet;
import com.test.tyrelocation.service.*;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date: 2019/9/22 15:58
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Controller
@RequestMapping("/operation")
public class OperationController extends BaseController{

    @Autowired
    private OperationService operationService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private SysMessageService sysMessageService;

    @ResponseBody
    @RequestMapping("/test")
    public Map<String, String> test(@RequestBody Image img) throws IOException {
        Map<String, String> result = new HashMap<>();
        String suffix=img.getData().substring(img.getData().indexOf("/")+1, img.getData().indexOf(";"));
        if (!("jpg".equals(suffix) || "png".equals(suffix) || "bmp".equals(suffix)|| "jpeg".equals(suffix))){
            return result;
        }
        img.setRepository("E:\\logs");
        img.setSuffix(suffix);
        String value = operationService.test(img);
        result.put("data", value);
        return result;
    }


    @RequestMapping("/index")
    public String index(Model model){
        Long userId = getUserId();
        Wallet wallet = walletService.getWallet(userId);
        int voucherNum = voucherService.getVoucherCount(userId);
        int payNum = orderService.getPayOrderCount(userId);
        int msgNum = messageService.getMessageCount(userId);
        int msgUnReadNum = messageService.getUnReadMessageCount(userId);
        Map<String, List<SysMessage>> sysMsg = sysMessageService.getMessageIndex(userId);
        model.addAttribute("money", wallet.getBalance());
        model.addAttribute("voucher", voucherNum);
        model.addAttribute("invoice", wallet.getInvoice());
        model.addAttribute("pay", payNum);
        model.addAttribute("msg", msgNum);
        model.addAttribute("msgUnReadNum",msgUnReadNum);
        model.addAttribute("sysMsg", sysMsg);
        model.addAttribute("username", getUsername());
        return "index3";
        //return "/application/applicationList";

    }

    @RequestMapping("/detection /{access_token}")
    public ResponseRet<List<Map<String,Double>>> detection(@PathVariable String access_token, @RequestBody Image img){
        return null;
    }

}
