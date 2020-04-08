package com.test.testbuyscheduler.stock.dao;

import com.test.testbuyscheduler.entity.StockFlow;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


public interface StockFlowMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StockFlow record);

    int insertSelective(StockFlow record);

    StockFlow selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockFlow record);

    int updateByPrimaryKey(StockFlow record);

    List<Long> selectDistinctSkuId();
}