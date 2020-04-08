package com.test.testbuystock.stock.service;

import com.test.testbuystock.stock.entity.Stock;
import com.test.testbuystock.stock.entity.StockReduce;

import java.util.List;
import java.util.Map;

/**
 * @author JackLei
 * @Title: StockService
 * @ProjectName testBuy
 * @Description:
 * @date 2018/6/2116:54
 **/
public interface StockService {
    int queryStock(Long skuId);

    Map<Long,Integer> reduceStock(List<StockReduce> reduceList);
}
