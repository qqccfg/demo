package com.test.tyrelocation.common.aspect;

import com.test.tyrelocation.common.constant.Constants;
import com.test.tyrelocation.common.page.VoucherPageBean;
import com.test.tyrelocation.entity.Voucher;
import com.test.tyrelocation.repository.VoucherMapper;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Date: 2020/3/15 11:37
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Aspect
@Component
public class VoucherAspect {

    @Autowired
    private VoucherMapper voucherMapper;

    public VoucherAspect(){
        System.out.println("VoucherAspect 来了");
    }

    @Pointcut("execution(* com.test.tyrelocation.repository.VoucherMapper.selectPageByUserId(..)) && args(voucherPageBean, userId)")
    private void pointcut(VoucherPageBean voucherPageBean, Long userId){}

    @Before(value = "pointcut(voucherPageBean, userId)")
    public void updateVoucherStatus(VoucherPageBean voucherPageBean, Long userId){
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
    }
}
