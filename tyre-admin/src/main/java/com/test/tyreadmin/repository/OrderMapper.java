package com.test.tyreadmin.repository;

import com.test.tyreadmin.common.page.PageQueryBean;
import com.test.tyreadmin.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    List<Order> selectByUserId(Long userId);

    Integer selectCountByUserId(Long userId);

    List<Order> selectPageByUserId(Long userId, PageQueryBean pageQueryBean);
}