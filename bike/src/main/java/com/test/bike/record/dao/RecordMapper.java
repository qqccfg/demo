package com.test.bike.record.dao;

import com.test.bike.record.entity.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
public interface RecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Record record);

    int insertSelective(Record record);

    Record selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Record record);

    int updateByPrimaryKey(Record record);

    Record selectByUserId(long userId);

    Record selectByBikeNumber(Integer bikeNumber);

    List<Record> selectList(@Param("userId") Long userId, @Param("id") Long id);
}