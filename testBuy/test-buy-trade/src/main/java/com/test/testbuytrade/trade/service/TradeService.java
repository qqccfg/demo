package com.test.testbuytrade.trade.service;

import com.test.testbuytrade.trade.entity.TradeItem;

import java.util.List;

/**
 * @author JackLei
 * @Title: TradeService
 * @ProjectName testBuy
 * @Description:
 * @date 2018/6/2113:57
 **/
public interface TradeService {
    List<TradeItem> order(List<TradeItem> tradeItem);
}
