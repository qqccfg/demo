package com.test.testbuytrade.trade.dao;

import com.test.testbuytrade.trade.entity.TradeItem;

public interface TradeItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TradeItem record);

    int insertSelective(TradeItem record);

    TradeItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TradeItem record);

    int updateByPrimaryKey(TradeItem record);
}