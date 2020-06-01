package com.test.tyrelocation.controller;

import com.test.tyrelocation.common.emum.ResponseEnum;
import com.test.tyrelocation.common.page.InvoicePageBean;
import com.test.tyrelocation.common.response.ResponseRet;
import com.test.tyrelocation.entity.Address;
import com.test.tyrelocation.entity.Invoice;
import com.test.tyrelocation.entity.Wallet;
import com.test.tyrelocation.repository.InvoiceMapper;
import com.test.tyrelocation.service.AddressService;
import com.test.tyrelocation.service.InvoiceService;
import com.test.tyrelocation.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Date: 2019/12/17 16:03
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Controller
@RequestMapping("/wallet")
public class WalletController extends BaseController{

    @Autowired
    private AddressService addressService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private WalletService walletService;

    @GetMapping("/address")
    public String address(@RequestParam(value = "page",defaultValue = "1") int page,
                          @RequestParam(value = "status", defaultValue = "1") int status,
                          Model model){
        Map<String, Object> data = invoiceData(page, status);
        model.addAttribute("data", data);
        model.addAttribute("body","invoice");
        model.addAttribute("adsPage", true);
        return "/user/account";
    }

    @PostMapping("/address/add")
    @ResponseBody
    public ResponseRet addAddress(@Valid @RequestBody Address address, Model model){
        addressService.addAddress(getUserId(), address);
        return new ResponseRet(ResponseEnum.OK);
    }

    @RequestMapping("/address/delete")
    public String deleteAddress(@RequestParam(value = "page",defaultValue = "1") int page,
                                @RequestParam(value = "status", defaultValue = "1") int status,
                                Long id, Model model){
        addressService.deleteAddress(getUserId(),id);
        Map<String, Object> data = invoiceData(page, status);
        model.addAttribute("data", data);
        model.addAttribute("body","invoice");
        model.addAttribute("adsPage", true);
        return "/user/account";
    }
    @RequestMapping("/address/update/{id}")
    @ResponseBody
    public ResponseRet updateAddress(@Valid @RequestBody Address address, @PathVariable("id") Long id, Model model){
        address.setId(id);
        addressService.updateAddress(getUserId(), address);
        return new ResponseRet(ResponseEnum.OK);
    }
    @RequestMapping("/address/query/{id}")
    @ResponseBody
    public ResponseRet<Address> queryToOne(@PathVariable("id") Long id){
        Address address = addressService.queryToOne(getUserId(), id);
        return new ResponseRet<>(ResponseEnum.OK, address);
    }

    @RequestMapping("/address/default")
    public String setAddressDefault(@RequestParam(value = "page",defaultValue = "1") int page,
                                    @RequestParam(value = "status", defaultValue = "1") int status,
                                    Long id, Model model){
        addressService.setDefaultAddress(getUserId(), id);
        Map<String, Object> data = invoiceData(page, status);
        model.addAttribute("data", data);
        model.addAttribute("body","invoice");
        model.addAttribute("adsPage", true);
        return "/user/account";
    }

    @RequestMapping("/invoice")
    public String invoiceList(@RequestParam(value = "page",defaultValue = "1") int page,
                              @RequestParam(value = "status", defaultValue = "1") int status,
                              Model model){
        Map<String, Object> data = invoiceData(page, status);
        model.addAttribute("data", data);
        model.addAttribute("adsPage", false);
        model.addAttribute("body","invoice");
        return "/user/account";
    }

    @RequestMapping("/invoice/apply")
    public String goApplyInvoice(@RequestParam("money")Float money, Model model){
        List<Address> addresses = addressService.queryToAll(getUserId());
        HashMap<String, Object> data = new HashMap<>();
        data.put("invoiceVal",0);
        data.put("money",money);
        model.addAttribute("body","applyInvoice");
        model.addAttribute("addresses",addresses);
        model.addAttribute("data",data);
        return "/user/account";
    }

    @RequestMapping("/invoice/doApply")
    @ResponseBody
    public ResponseRet doApplyInvoice(@RequestBody @Valid Invoice invoice){
        invoiceService.apply(getUserId(), invoice);
        return new ResponseRet(ResponseEnum.OK);
    }

    @RequestMapping("/invoice/cancel")
    public String cancelInvoice(@RequestParam(value = "page",defaultValue = "1") int page,
                                @RequestParam(value = "status", defaultValue = "1") int status,
                                Long id, Model model){
        invoiceService.cancel(getUserId(), id);
        Map<String, Object> data = invoiceData(page, status);
        model.addAttribute("data", data);
        model.addAttribute("adsPage", false);
        model.addAttribute("body","invoice");
        return "/user/account";
    }

    private Map<String, Object> invoiceData(int page, int status){
        HashMap<String, Object> data = new HashMap();
        InvoicePageBean invoicePageBean = new InvoicePageBean(status, page);
        InvoicePageBean result = invoiceService.getAllByPage(getUserId(), invoicePageBean);
        List<Address> addresses = addressService.queryToAll(getUserId());
        Wallet wallet = walletService.getWallet(getUserId());
        data.put("money",0);
        data.put("invoiceVal", wallet.getInvoice());
        data.put("result", result);
        data.put("addresses", addresses);
        return data;
    }
}
