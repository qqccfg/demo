package com.test.tyreadmin.service;

import com.test.tyreadmin.common.constant.Constants;
import com.test.tyreadmin.common.emum.ExceptionEnum;
import com.test.tyreadmin.common.exception.TyreAdminException;
import com.test.tyreadmin.common.page.InvoicePageBean;
import com.test.tyreadmin.entity.Address;
import com.test.tyreadmin.entity.Invoice;
import com.test.tyreadmin.entity.Message;
import com.test.tyreadmin.repository.AddressMapper;
import com.test.tyreadmin.repository.InvoiceMapper;
import com.test.tyreadmin.repository.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Date: 2020/4/25 23:41
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Service
public class InvoiceServiceImpl implements InvoiceService{

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public InvoicePageBean invoiceList(InvoicePageBean pageBean) {
        Integer count = invoiceMapper.selectCountByStatus(pageBean.getStatus());
        if (Objects.isNull(count)){
            count = 0;
        }
        pageBean.setTotalRows(count);
        List<Invoice> invoices = invoiceMapper.selectPageByStatus(pageBean);
        pageBean.setItems(invoices);
        return pageBean;
    }

    @Override
    public Address addressInfo(Long id) {
        return addressMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateInvoiceStatus(Long id, int status) {

        Invoice invoice = invoiceMapper.selectByPrimaryKey(id);
        if (Objects.isNull(invoice)){
            throw new TyreAdminException(ExceptionEnum.PARAMETER_ERROR);
        }
        invoice.setStatus(status);
        invoiceMapper.updateByPrimaryKeySelective(invoice);
        if (status== Constants.INVOICE_SEND_SUCCESS){
            Message message = new Message();
            message.setUserId(invoice.getUserId());
            message.setTitle("发票已经通过物流发送");
            message.setBody("物流单号为：xxxx,请及时关注物流信息接收快递");
            message.setCategory(Constants.MSG_PRODUCT);
        }

    }

    @Override
    public Invoice invoiceInfo(Long id) {
        return invoiceMapper.selectByPrimaryKey(id);
    }

}
