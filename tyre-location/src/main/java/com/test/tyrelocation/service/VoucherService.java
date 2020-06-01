package com.test.tyrelocation.service;

import com.test.tyrelocation.common.page.PageQueryBean;
import com.test.tyrelocation.common.page.VoucherPageBean;
import com.test.tyrelocation.entity.Voucher;

import java.util.List;

/**
 * @Date: 2019/11/22 15:42
 * @Author: JackLei
 * @Description:
 * @Version:
 */
public interface VoucherService {

    int getVoucherCount(Long userId);

    VoucherPageBean getVouchers(VoucherPageBean voucherPageBean, Long userId);

    List<Voucher> getUsableVouchers(Long userId);
}
