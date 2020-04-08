package com.test.testbuytrade.product.dao;

import com.test.testbuytrade.product.entity.ProductSku;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Set;

public interface ProductSkuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductSku record);

    int insertSelective(ProductSku record);

    ProductSku selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductSku record);

    int updateByPrimaryKey(ProductSku record);

    List<ProductSku> selectBySkuIdList(@Param("longs") Set<Long> longs);
}