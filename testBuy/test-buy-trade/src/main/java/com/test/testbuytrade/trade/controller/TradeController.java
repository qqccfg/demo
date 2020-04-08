package com.test.testbuytrade.trade.controller;

import com.test.testbuytrade.common.rese.ApiResult;
import com.test.testbuytrade.trade.entity.TradeItem;
import com.test.testbuytrade.trade.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author JackLei
 * @Title: TradeController
 * @ProjectName testBuy
 * @Description:
 * @date 2018/6/2113:55
 **/
@RestController
@RequestMapping("trade")
public class TradeController {
    /**
     * @Author JackLei
     * @Date cteate in 2018/6/28 16:17
     * @Description 下单
    **/
    @Autowired
    private TradeService tradeService;

    @RequestMapping("/order")
    public ApiResult<List<TradeItem>> creadeOrder(@RequestBody List<TradeItem> tradeItem){
        ApiResult<List<TradeItem>> result=new ApiResult<>(200,"下单成功");
        List<TradeItem> tradeItemList=tradeService.order(tradeItem);
        result.setData(tradeItemList);
        return result;
    }
}
