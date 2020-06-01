package com.test.tyrelocation.service;

import com.test.tyrelocation.common.Exception.TyreException;
import com.test.tyrelocation.common.emum.ExceptionEnum;
import com.test.tyrelocation.common.page.InvoicePageBean;
import com.test.tyrelocation.entity.Address;
import com.test.tyrelocation.entity.Invoice;
import com.test.tyrelocation.entity.Order;
import com.test.tyrelocation.entity.Wallet;
import com.test.tyrelocation.repository.AddressMapper;
import com.test.tyrelocation.repository.InvoiceMapper;
import com.test.tyrelocation.repository.OrderMapper;
import com.test.tyrelocation.repository.WalletMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Date: 2019/11/28 13:59
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Service
public class InvoiceServiceImpl implements InvoiceService{

    private final int APPLYING = 1;
    private final int FAIL = 4;

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private WalletMapper walletMapper;

    @Override
    public InvoicePageBean getAllByPage(Long userId, InvoicePageBean bean) {
        InvoicePageBean result = new InvoicePageBean(bean.getStatus(), bean.getCurrentPage());
        Integer count = invoiceMapper.selectCountByUserIdAndStatus(userId, bean.getStatus());
        if (Objects.isNull(count)){
            count = 0;
        }
        bean.setTotalRows(count);
        List<Invoice> invoices = invoiceMapper.selectByUserIdAndStatus(userId ,bean);
        result.setItems(invoices);
        result.setTotalRows(count);
        return result;
    }

    @Override
    public void cancel(Long userId, Long id) {
        Invoice invoice = invoiceMapper.selectByPrimaryKeyAndUserId(userId, id);
        if (Objects.isNull(invoice)){
            throw new TyreException(ExceptionEnum.PARAMETER_ILLEGALITY);
        }else if (invoice.getStatus() != APPLYING){
            throw new TyreException(ExceptionEnum.PARAMETER_ILLEGALITY);
        }
        invoice.setStatus(FAIL);
        invoiceMapper.updateByPrimaryKey(invoice);
    }

    @Override
    @Transactional
    public void apply(Long userId, Invoice invoice) {
        Address address = addressMapper.selectByPrimaryKeyAndUserId(userId, invoice.getAddressId());
        if (Objects.isNull(address)){
            throw new TyreException(ExceptionEnum.PARAMETER_ILLEGALITY);
        }
        Wallet wallet = walletMapper.selectByUserId(userId);
        int ret = wallet.getInvoice().compareTo(invoice.getMoney());
        if (ret==-1){
            throw new TyreException(ExceptionEnum.PARAMETER_ILLEGALITY);
        }

        wallet.setInvoice(wallet.getInvoice().subtract(invoice.getMoney()));
        walletMapper.updateByPrimaryKeySelective(wallet);
        invoice.setUserId(userId);
        invoiceMapper.insertSelective(invoice);
    }
}
