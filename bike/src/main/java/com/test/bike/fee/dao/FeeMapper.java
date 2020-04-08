package com.test.bike.fee.dao;

import com.test.bike.fee.entity.Fee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FeeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Fee record);

    int insertSelective(Fee record);

    Fee selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Fee record);

    int updateByPrimaryKey(Fee record);

    Fee selectByType(Byte type);
}