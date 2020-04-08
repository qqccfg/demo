package com.test.testbuytrade.product.dao;

import com.test.testbuytrade.product.entity.Category;
import com.test.testbuytrade.product.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ProductMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> selectBycategoryId(Long categoryId);

    Product selectByPrimaryKeyWithSku(Long productId);
}