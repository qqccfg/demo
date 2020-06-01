package com.test.tyrelocation.service;

import com.test.tyrelocation.common.Exception.TyreException;
import com.test.tyrelocation.common.emum.ExceptionEnum;
import com.test.tyrelocation.common.page.PageQueryBean;
import com.test.tyrelocation.entity.PayRecord;
import com.test.tyrelocation.entity.Wallet;
import com.test.tyrelocation.repository.PayRecordMapper;
import com.test.tyrelocation.repository.WalletMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * @Date: 2020/4/23 19:38
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Service
public class PayRecordServiceImpl implements PayRecordService{

    @Autowired
    private PayRecordMapper payRecordMapper;

    @Autowired
    private WalletMapper walletMapper;

    @Override
    @Transactional
    public void createPayRecord(PayRecord payRecord) {
        payRecordMapper.insertSelective(payRecord);
        Wallet wallet = walletMapper.selectByUserId(payRecord.getUserId());
        if (Objects.isNull(wallet)){
            throw new TyreException(ExceptionEnum.PARAMETER_ILLEGALITY);
        }
        wallet.setBalance(wallet.getBalance().add(payRecord.getMoney()));
        walletMapper.updateByPrimaryKeySelective(wallet);
    }

    @Override
    public PageQueryBean getPayRecordListByUserId(Long userId, PageQueryBean pageQueryBean) {
        Integer count = payRecordMapper.selectCountByUserId(userId);
        if (Objects.isNull(count)){
            count = 0;
        }
        pageQueryBean.setTotalRows(count);
        List<PayRecord> payRecords = payRecordMapper.selectPageByUserId(userId, pageQueryBean);
        pageQueryBean.setItems(payRecords);
        return pageQueryBean;
    }


}
