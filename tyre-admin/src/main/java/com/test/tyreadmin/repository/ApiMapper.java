package com.test.tyreadmin.repository;

import com.test.tyreadmin.common.page.PageQueryBean;
import com.test.tyreadmin.entity.Api;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApiMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Api record);

    int insertSelective(Api record);

    Api selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Api record);

    int updateByPrimaryKey(Api record);

    List<Api> selectAll();

    Integer selectAllCount();

    List<Api> selectPage(@Param("pageQueryBean") PageQueryBean pageQueryBean);
}