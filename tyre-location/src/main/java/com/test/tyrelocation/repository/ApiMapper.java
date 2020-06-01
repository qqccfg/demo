package com.test.tyrelocation.repository;


import com.test.tyrelocation.entity.Api;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApiMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Api record);

    int insertSelective(Api record);

    Api selectByPrimaryKey(Long id);

    List<Api> selectByIdBatch(@Param("apis") List<Api> apis);

    int updateByPrimaryKeySelective(Api record);

    int updateByPrimaryKey(Api record);

    List<Api> selectAll();

    List<Api> selectByApplicationId(@Param("applicationId") Long applicationId);
}