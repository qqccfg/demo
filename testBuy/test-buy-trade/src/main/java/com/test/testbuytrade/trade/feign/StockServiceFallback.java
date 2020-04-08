package com.test.testbuytrade.trade.feign;

import com.test.testbuytrade.common.constants.Constants;
import com.test.testbuytrade.common.rese.ApiResult;
import com.test.testbuytrade.trade.entity.StockReduce;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author JackLei
 * @Title: StockServiceFallback
 * @ProjectName testBuy
 * @Description:
 * @date 2018/6/2821:37
 **/
public class StockServiceFallback implements SockServiceClient{
    @Override
    public ApiResult<Map<Long, Integer>> reduceStock(List<StockReduce> stockReduceList) {
        ApiResult<Map<Long,Integer>> result=new ApiResult<>(Constants.RESP_STATUS_BADREQUEST,"请求失败，稍后再试");
        result.setData(Collections.EMPTY_MAP);
        return result;
    }
}
