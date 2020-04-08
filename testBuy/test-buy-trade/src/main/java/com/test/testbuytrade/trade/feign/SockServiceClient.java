package com.test.testbuytrade.trade.feign;

import com.test.testbuytrade.common.rese.ApiResult;
import com.test.testbuytrade.trade.entity.StockReduce;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.util.List;
import java.util.Map;

/**
 * @author JackLei
 * @Title: SockServiceClient
 * @ProjectName testBuy
 * @Description:
 * @date 2018/6/2821:31
 **/
@FeignClient(name = "stock-service",fallback = StockServiceFallback.class)
public interface SockServiceClient {
    @RequestMapping(value = "/stock/reduce",method = RequestMethod.POST)
    ApiResult<Map<Long,Integer>> reduceStock(@RequestBody List<StockReduce> stockReduceList);
}
