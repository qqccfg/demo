package com.test.bike.wallet.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
@Data
public class Wallet implements Serializable {
    private Long id;

    private Long userId;

    private BigDecimal remainSum;

    private BigDecimal deposit;


}