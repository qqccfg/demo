package com.test.tyrelocation.service;

import com.test.tyrelocation.common.constant.Constants;
import com.test.tyrelocation.common.page.PageQueryBean;
import com.test.tyrelocation.common.page.VoucherPageBean;
import com.test.tyrelocation.entity.Voucher;
import com.test.tyrelocation.repository.VoucherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Date: 2019/11/22 15:45
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Service
public class VoucherServiceImpl implements VoucherService{

    private final int UN_USER = 1;

    @Autowired
    private VoucherMapper voucherMapper;

    @Override
    public int getVoucherCount(Long userId) {
        Integer count = voucherMapper.selectCountByUserIdAndStatus(UN_USER, userId);
        return count==null ? 0 : count;
    }

    @Override
    public VoucherPageBean getVouchers(VoucherPageBean voucherPageBean, Long userId) {
        VoucherPageBean result = new VoucherPageBean();
        Integer count = voucherMapper.selectCountByUserIdAndStatus(voucherPageBean.getStatus(), userId);
        if (Objects.isNull(count)){
            count = 0;
        }
        voucherPageBean.setTotalRows(count);
        List<Voucher> vouchers = voucherMapper.selectPageByUserId(voucherPageBean, userId);
        result.setCurrentPage(voucherPageBean.getCurrentPage());
        result.setTotalRows(count);
        result.setStatus(voucherPageBean.getStatus());
        result.setItems(vouchers);
        return result;
    }

    @Override
    @Transactional
    public List<Voucher> getUsableVouchers(Long userId) {
        List<Voucher> vouchers = voucherMapper.selectByUserIdAndEnable(userId);
        Date currentTime = new Date();
        List<Voucher> modifyVouchers = vouchers.stream().filter(voucher -> {
            return voucher.getEndTime().getTime() < currentTime.getTime();
        }).map(voucher -> {
            voucher.setStatus(Constants.VOUCHER_OVERDUE);
            return voucher;
        }).collect(Collectors.toList());
        for (Voucher voucher : modifyVouchers){
            voucherMapper.updateByPrimaryKeySelective(voucher);
        }
        return voucherMapper.selectByUserIdAndEnable(userId);
    }
}
