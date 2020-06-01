package com.test.tyrelocation.service;

import com.test.tyrelocation.entity.Wallet;

import java.math.BigDecimal;

/**
 * @Date: 2019/11/22 15:27
 * @Author: JackLei
 * @Description:
 * @Version:
 */
public interface WalletService {

    Wallet getWallet(Long userId);

    void topUpAmount(Long userId, int amount);


}
