package com.test.testbuytrade.trade.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class Trade implements Serializable {
    private Long id;

    private Long userUuid;

    private Long tradeNo;

    private Long payment;

    private Byte paymenyType;

    private Byte status;

    private Date paymentTime;

    private Date closeTime;

    private Date createTime;

    private Date updateTime;


}