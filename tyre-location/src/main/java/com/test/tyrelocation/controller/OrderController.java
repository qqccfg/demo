package com.test.tyrelocation.controller;

import com.test.tyrelocation.common.Exception.TyreException;
import com.test.tyrelocation.common.emum.ExceptionEnum;
import com.test.tyrelocation.common.emum.ResponseEnum;
import com.test.tyrelocation.common.page.InvoicePageBean;
import com.test.tyrelocation.common.page.OrderPageBean;
import com.test.tyrelocation.common.page.PageQueryBean;
import com.test.tyrelocation.common.page.VoucherPageBean;
import com.test.tyrelocation.common.response.ResponseRet;
import com.test.tyrelocation.common.tool.Generator;
import com.test.tyrelocation.entity.Address;
import com.test.tyrelocation.entity.Order;
import com.test.tyrelocation.entity.Voucher;
import com.test.tyrelocation.entity.Wallet;
import com.test.tyrelocation.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Date: 2019/11/22 20:18
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController{

    private final int MONTH_NUM = 10;

    @Autowired
    private OrderService orderService;

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private ApiService apiService;

    @Autowired
    private PayRecordService payRecordService;



    @RequestMapping("/test")
    public ResponseRet test(){
        orderService.getPayOrderCount(1L);
        return new ResponseRet(ResponseEnum.OK);
    }

    @GetMapping("/consume")
    @ResponseBody
    public ResponseRet<Map<String, Integer>> pastConsume(){
       Map<String, Integer> data = orderService.getPastForMonth(MONTH_NUM, getUserId());
       return new ResponseRet<>(ResponseEnum.OK, data);
    }

    @RequestMapping("/account")
    public String account(Model model){
        Wallet wallet = walletService.getWallet(getUserId());
        model.addAttribute("money", wallet.getBalance());
        model.addAttribute("invoice", wallet.getInvoice());
        int voucherCount = voucherService.getVoucherCount(getUserId());
        model.addAttribute("voucherCount", voucherCount);
        int todayPast = orderService.getPastToday(getUserId());
        int currentMonthPast = orderService.getPastCurrentMonth(getUserId());
        model.addAttribute("todayPast", todayPast);
        model.addAttribute("currentMonthPast", currentMonthPast);
        int payNum = orderService.getPayOrderCount(getUserId());
        model.addAttribute("payNum", payNum);
        model.addAttribute("body", "financeIndex");
        model.addAttribute("data", invoiceDataEmpty());
        return "user/account";
    }

    @RequestMapping("/voucher")
    public String getVouchers(@RequestParam(value = "status",defaultValue = "1") int status,
                              @RequestParam(value = "page", defaultValue = "1") int page, Model model){
        if (!(0<=status && status<4)){
            throw new TyreException(ExceptionEnum.PARAMETER_ILLEGALITY);
        }
        VoucherPageBean voucherPageBean = new VoucherPageBean(status, page);
        VoucherPageBean result = voucherService.getVouchers(voucherPageBean, getUserId());
        int voucherCount = voucherService.getVoucherCount(getUserId());
        model.addAttribute("voucherCount", voucherCount);
        model.addAttribute("result", result);
        model.addAttribute("body", "voucher");
        model.addAttribute("data", invoiceDataEmpty());
        return "/user/account";
    }


    @RequestMapping("/list")
    public String orderList(@RequestParam(value = "page",defaultValue = "1") int page,
                            @RequestParam(value = "status", defaultValue = "0") int status,
                            Model model){
        OrderPageBean orderPageBean = new OrderPageBean(status, page);
        OrderPageBean result = orderService.getListByPage(getUserId(), orderPageBean);
        List<Long> productIds = result.getItems().stream().map(val->{
            return  ((Order)val).getProductId();
        }).collect(Collectors.toList());
        List<String> productNames = new ArrayList<>();
        for (Long id : productIds){
            productNames.add(apiService.queryName(id));
        }
        model.addAttribute("result", result);
        model.addAttribute("body", "order");
        model.addAttribute("productNames", productNames);
        model.addAttribute("data", invoiceDataEmpty());
        return "/user/account";

    }
    @RequestMapping("/payRecord")
    public String payRecord(@RequestParam(value = "page",defaultValue = "1") int page, Model model){
        PageQueryBean pageQueryBean = new PageQueryBean();
        pageQueryBean.setCurrentPage(page);
        PageQueryBean result = payRecordService.getPayRecordListByUserId(getUserId(), pageQueryBean);
        model.addAttribute("result", result);
        model.addAttribute("body", "payRecord");
        model.addAttribute("data", invoiceDataEmpty());
        return "/user/account";
    }
    @RequestMapping("/cancel")
    public String cancelOrder(@RequestParam(value = "page",defaultValue = "1") int page,
                              @RequestParam(value = "status", defaultValue = "0") int status,
                              Long orderId,
                              Model model){

        orderService.cancelOrder(orderId, getUserId());
        OrderPageBean orderPageBean = new OrderPageBean(status, page);
        OrderPageBean result = orderService.getListByPage(getUserId(), orderPageBean);
        List<Long> productIds = result.getItems().stream().map(val->{
            return  ((Order)val).getProductId();
        }).collect(Collectors.toList());
        List<String> productNames = new ArrayList<>();
        for (Long id : productIds){
            productNames.add(apiService.queryName(id));
        }
        model.addAttribute("result", result);
        model.addAttribute("body", "order");
        model.addAttribute("productNames", productNames);
        model.addAttribute("data", invoiceDataEmpty());
        return "/user/account";
    }
    @RequestMapping("/buy")
    public String buyPage(Long apiId, Model model){
        String apiName = apiService.queryName(apiId);
        model.addAttribute("apiName", apiName);
        model.addAttribute("apiId", apiId);
        return "/buy/buyPage";
    }

    @RequestMapping("/placeOrder")
    public String placeOrder(String data, Long apiId, Model model){
        List<Integer> numbers = new ArrayList<>();
        String apiName = apiService.queryName(apiId);
        String[] dataArray = data.split("H");
        try {
            for (String val : dataArray){
                numbers.add(Integer.parseInt(val));
            }
        }catch (Exception e){
            throw new TyreException(ExceptionEnum.PARAMETER_ILLEGALITY);
        }
        int sumMoney = Generator.calculatePrice(data);
        List<Voucher> vouchers = voucherService.getUsableVouchers(getUserId());
        Wallet wallet = walletService.getWallet(getUserId());
        model.addAttribute("apiId", apiId);
        model.addAttribute("apiName", apiName);
        model.addAttribute("numbers", numbers);
        model.addAttribute("vouchers",vouchers);
        model.addAttribute("money",wallet.getBalance());
        model.addAttribute("sumMoney", sumMoney);
        model.addAttribute("data", data);
        return "/buy/placeOrder";
    }
    @RequestMapping("/wallet/pay")
    @ResponseBody
    public ResponseRet walletPay(String data, Long apiId, Long voucherId){
        int sumMoney = Generator.calculatePrice(data);
        int number = Generator.calculateNumber(data);
        orderService.walletPay(sumMoney,number, apiId, voucherId, getUserId());
       return new ResponseRet(ResponseEnum.OK);
    }

    private Map<String, Object> invoiceDataEmpty(){
        HashMap<String, Object> data = new HashMap();
        data.put("money", 0);
        data.put("invoiceVal", null);
        return data;
    }

}
