package com.test.testbuytrade.product.dao;

import com.test.testbuytrade.product.entity.SpuPropertyOption;

public interface SpuPropertyOptionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SpuPropertyOption record);

    int insertSelective(SpuPropertyOption record);

    SpuPropertyOption selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SpuPropertyOption record);

    int updateByPrimaryKey(SpuPropertyOption record);
}