package com.test.tyrelocation.service;

import com.test.tyrelocation.common.Exception.TyreException;
import com.test.tyrelocation.common.emum.ExceptionEnum;
import com.test.tyrelocation.entity.Wallet;
import com.test.tyrelocation.repository.WalletMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @Date: 2019/11/22 15:30
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Service
public class WalletServiceImpl implements WalletService{

    @Autowired
    private WalletMapper walletMapper;

    @Override
    public Wallet getWallet(Long userId) {
        return walletMapper.selectByUserId(userId);
    }

    @Override
    public void topUpAmount(Long userId, int amount) {
        Wallet wallet = walletMapper.selectByUserId(userId);
        if (Objects.isNull(wallet)){
            throw new TyreException(ExceptionEnum.PARAMETER_ILLEGALITY);
        }
        wallet.setBalance(wallet.getBalance().add(new BigDecimal(amount)));
        walletMapper.insertSelective(wallet);
    }
}
