package com.test.tyrelocation.repository;



import com.test.tyrelocation.entity.ApplicationApi;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApplicationApiMapper {
    int deleteByPrimaryKey(Long id);

    int deleteByApplicationId(Long id);

    int insert(ApplicationApi record);

    int insertSelective(ApplicationApi record);

    int insertBatch(@Param("records") List<ApplicationApi> records);

    ApplicationApi selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplicationApi record);

    int updateByPrimaryKey(ApplicationApi record);
}