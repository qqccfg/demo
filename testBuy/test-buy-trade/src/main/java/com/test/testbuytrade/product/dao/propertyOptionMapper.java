package com.test.testbuytrade.product.dao;

import com.test.testbuytrade.product.entity.propertyOption;

public interface propertyOptionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(propertyOption record);

    int insertSelective(propertyOption record);

    propertyOption selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(propertyOption record);

    int updateByPrimaryKey(propertyOption record);
}