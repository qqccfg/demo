package com.test.testbuystock.stock.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class StockFlow implements Serializable {
    private Long id;

    private Long skuId;

    private Integer stockBefore;

    private Integer stockAfter;

    private Integer stockChange;

    private Integer lockStockBefore;

    private Integer lockStockAfter;

    private Integer lockStockChange;

    private Date createTime;

    private Date updateTime;

    private Long orderNo;


}