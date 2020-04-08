package com.test.bike.bike.dao;

import com.test.bike.bike.entity.Bike;
import com.test.bike.bike.entity.GenerateBakeNumber;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BikeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Bike record);

    int insertSelective(Bike record);

    Bike selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Bike record);

    int updateByPrimaryKey(Bike record);

    int generateBikeNum(GenerateBakeNumber bakeNumber);

    Bike selectByBikeNumber(Integer bikeNumber);
}