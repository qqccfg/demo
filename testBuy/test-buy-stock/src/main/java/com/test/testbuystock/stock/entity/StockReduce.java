package com.test.testbuystock.stock.entity;

import lombok.Data;

import java.util.List;

/**
 * @author JackLei
 * @Title: StockReduce
 * @ProjectName testBuy
 * @Description:
 * @date 2018/6/2116:59
 **/
@Data
public class StockReduce {
    private Long orderNo;

    private Long skuId;

    private Integer reduceCount;
}
