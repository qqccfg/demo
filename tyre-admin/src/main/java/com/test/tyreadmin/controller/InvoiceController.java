package com.test.tyreadmin.controller;

import com.test.tyreadmin.common.emum.ExceptionEnum;
import com.test.tyreadmin.common.emum.ResponseEnum;
import com.test.tyreadmin.common.exception.TyreAdminException;
import com.test.tyreadmin.common.page.InvoicePageBean;
import com.test.tyreadmin.common.response.ResponseRet;
import com.test.tyreadmin.entity.Address;
import com.test.tyreadmin.entity.Invoice;
import com.test.tyreadmin.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Date: 2020/4/26 8:52
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@RequestMapping("invoice")
@Controller
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @RequestMapping("/invoiceList")
    public String invoiceList(@RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "status", defaultValue = "1") int status, Model model){
        InvoicePageBean invoicePageBean = new InvoicePageBean(page, status);
        InvoicePageBean result = invoiceService.invoiceList(invoicePageBean);
        model.addAttribute("result", result);
        return "/invoice/invoiceList";
    }

    @RequestMapping("/update/status")
    @ResponseBody
    public ResponseRet updateInvoiceStatus(Long id, int status){
        if (!checkInvoiceStatus(status)){
            throw new TyreAdminException(ExceptionEnum.PARAMETER_ERROR);
        }
        invoiceService.updateInvoiceStatus(id, status);
        return new ResponseRet(ResponseEnum.OK);
    }

    @RequestMapping("/addressInfo")
    public String addressInfo(Long addressId, Long id, int page, int status, Model model){
        Address address = invoiceService.addressInfo(addressId);
        Invoice invoice = invoiceService.invoiceInfo(id);
        model.addAttribute("address", address);
        model.addAttribute("invoice", invoice);
        model.addAttribute("page", page);
        model.addAttribute("status", status);
        return "/invoice/addressInfo";
    }

    private boolean checkInvoiceStatus(int status){
        if (0<status && status<5){
            return true;
        }else {
            return false;
        }
    }
}
