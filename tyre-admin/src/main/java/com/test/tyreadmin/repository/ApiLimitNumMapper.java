package com.test.tyreadmin.repository;

import com.test.tyreadmin.common.page.PageQueryBean;
import com.test.tyreadmin.entity.ApiLimitNum;
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

    Integer selectCountByUserId(Long userId);

    List<ApiLimitNum> selectPageByUserId(Long userId, @Param("pageQueryBean") PageQueryBean pageQueryBean);
}