package com.test.testbuytrade.trade.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class TradeItem implements Serializable {
    private Long id;

    private Long userUuid;

    private Long tradeNo;

    private Integer sku;

    private String skuName;

    private Long currentPrice;

    private String skuImageUrl;

    private Integer quantity;

    private Long totalPrice;

    private Date createTime;

    private Date updateTime;



}