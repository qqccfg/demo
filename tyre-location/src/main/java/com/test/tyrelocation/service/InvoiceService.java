package com.test.tyrelocation.service;

import com.test.tyrelocation.common.page.InvoicePageBean;
import com.test.tyrelocation.entity.Invoice;

/**
 * @Date: 2019/11/28 13:58
 * @Author: JackLei
 * @Description:
 * @Version:
 */
public interface InvoiceService {



    InvoicePageBean getAllByPage(Long userId, InvoicePageBean invoicePageBean);

    void cancel(Long userId, Long id);

    void apply(Long userId, Invoice invoice);

}
