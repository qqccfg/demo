package com.test.tyrelocation.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.test.tyrelocation.common.Exception.TyreException;
import com.test.tyrelocation.common.config.AliPayConfig;
import com.test.tyrelocation.common.constant.Constants;
import com.test.tyrelocation.common.emum.ExceptionEnum;
import com.test.tyrelocation.common.tool.Generator;
import com.test.tyrelocation.entity.PayRecord;
import com.test.tyrelocation.entity.Wallet;
import com.test.tyrelocation.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

import static java.lang.System.out;
import static java.lang.System.setOut;

/**
 * @Date: 2020/2/19 18:55
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Controller
@RequestMapping("pay")
public class PayController extends BaseController{

    @Autowired
    private AlipayClient alipayClient;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private OrderService orderService;

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private PayRecordService payRecordService;


    @RequestMapping("/toPay")
    public void aliTopUp(@RequestParam("amount") int amount, HttpServletRequest httpRequest,
                         HttpServletResponse httpResponse) throws IOException {

        if (!checkAmount(amount)){
            throw new TyreException(ExceptionEnum.PARAMETER_ILLEGALITY);
        }
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AliPayConfig.return_url);
        alipayRequest.setNotifyUrl(AliPayConfig.notify_url);
        //订单号
        String out_trade_no = String.valueOf(Generator.getId());
        //订单名称，必填
        String subject = "充值";
        //商品描述，可空
        String body = String.valueOf(getUserId());

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String result = null;
        try {
            result = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        //输出
        httpResponse.setContentType("text/html;charset=" + AliPayConfig.charset);
        httpResponse.getWriter().write(result);//直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }

    @RequestMapping("/result/url")
    public String payResultUrl(HttpServletRequest request, Model model) throws UnsupportedEncodingException, AlipayApiException {
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AliPayConfig.alipay_public_key, AliPayConfig.charset, AliPayConfig.sign_type); //调用SDK验证签名
        if (signVerified){
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
        }else {
            return "/common/error";
        }
    }

    @RequestMapping("/notify/url")
    @ResponseBody
    public void payNotifyUrl(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException {
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AliPayConfig.alipay_public_key, AliPayConfig.charset, AliPayConfig.sign_type); //调用SDK验证签名

        if (signVerified){
            String tradeStatus = request.getParameter("trade_status");
            if ("TRADE_SUCCESS".equals(tradeStatus)){
                String amount = request.getParameter("total_amount");
                String tradeNo = request.getParameter("trade_no");
                String out_trade_no = request.getParameter("out_trade_no");
                String userId = request.getParameter("body");
                PayRecord payRecord = new PayRecord();
                payRecord.setUserId(Long.valueOf(userId));
                payRecord.setId(Long.valueOf(out_trade_no));
                payRecord.setMoney(new BigDecimal(Double.valueOf(amount)));
                payRecord.setPayType(Constants.PAY_ALIPAY);
                payRecord.setTransactionNumber(tradeNo);
                payRecordService.createPayRecord(payRecord);
                System.out.println("success");
            }
        }
    }
    private Map<String, Object> invoiceDataEmpty(){
        HashMap<String, Object> data = new HashMap();
        data.put("money", 0);
        data.put("invoiceVal", null);
        return data;
    }
    private boolean checkAmount(int amount){
        HashSet<Integer> set = new HashSet<>(Arrays.asList(10, 20, 50, 100, 1000));
        set.add(amount);
        if (set.size()>5){
            return false;
        }
        return true;
    };

}
