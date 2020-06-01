package com.test.tyrelocation.repository;


import com.test.tyrelocation.entity.Application;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApplicationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Application record);

    int insertSelective(Application record);

    int insertByNeed(Application record);

    Application selectByPrimaryKey(Long id);

    List<Application> selectByUserId(Long userId);

    int updateByPrimaryKeySelective(Application record);

    int updateByPrimaryKey(Application record);


}