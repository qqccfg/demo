package com.test.tyrelocation.repository;

import com.test.tyrelocation.entity.Api;
import com.test.tyrelocation.entity.ApiLimitNum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApiLimitNumMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApiLimitNum record);

    int insertSelective(ApiLimitNum record);

    ApiLimitNum selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApiLimitNum record);

    int updateByPrimaryKey(ApiLimitNum record);

    int insertBatch(@Param("apis") List<Api> apis, Long userId);

    List<ApiLimitNum> selectByUserId(Long userId);

    ApiLimitNum selectByApiId(Integer apiId);

    ApiLimitNum selectByUserIdAndApiId(Long userId, Long tyreApiId);
}