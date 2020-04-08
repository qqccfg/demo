package com.test.testbuystock.stock.controller;

import com.test.testbuystock.common.rese.ApiResult;
import com.test.testbuystock.stock.entity.Stock;
import com.test.testbuystock.stock.entity.StockReduce;
import com.test.testbuystock.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author JackLei
 * @Title: StockController
 * @ProjectName testBuy
 * @Description:
 * @date 2018/6/2116:52
 **/
@RestController
@RequestMapping("stock")
public class StockController {
    @Autowired
    private StockService stockService;
    /**
     * @Author JackLei
     * @Date cteate in 2018/6/21 16:57
     * @Description 扣减库存
    **/
    @RequestMapping("/reduce")
    public ApiResult<Map<Long,Integer>> reduceStock(@RequestBody List<StockReduce> reduceList){
        ApiResult<Map<Long,Integer>> result=new ApiResult<>(200,"扣减成功");
        Map<Long,Integer> reduceMap=stockService.reduceStock(reduceList);
        result.setData(reduceMap);
        return result;
    }
    /**
     * @Author JackLei
     * @Date cteate in 2018/6/21 20:10
     * @Description 查询单个的库存
    **/
    @RequestMapping("/query/{skuId}")
    public ApiResult<Stock> queryStock(@PathVariable Long skuId){
        ApiResult<Stock> result=new ApiResult<>(200,"查询成功");
        int stockCount=stockService.queryStock(skuId);
        Stock stock=new Stock();
        stock.setStock(stockCount);
        result.setData(stock);
        return result;
    }
}
