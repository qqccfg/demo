package com.test.tyreadmin.service;

import com.test.tyreadmin.common.page.InvoicePageBean;
import com.test.tyreadmin.entity.Address;
import com.test.tyreadmin.entity.Invoice;

/**
 * @Date: 2020/4/25 23:41
 * @Author: JackLei
 * @Description:
 * @Version:
 */
public interface InvoiceService {

    InvoicePageBean invoiceList(InvoicePageBean pageBean);

    Address addressInfo(Long id);

    void updateInvoiceStatus(Long id, int status);


    Invoice invoiceInfo(Long id);
}
