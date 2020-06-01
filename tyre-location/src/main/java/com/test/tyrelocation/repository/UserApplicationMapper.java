package com.test.tyrelocation.repository;

import com.test.tyrelocation.entity.UserApplication;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserApplicationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserApplication record);

    int insertSelective(UserApplication record);

    UserApplication selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserApplication record);

    int updateByPrimaryKey(UserApplication record);

    UserApplication selectByUIdAndAid(Long userId, Long applicationId);

    int deleteByApplicationId(Long applicationId);

    Integer selectApplicationNumByUserId(Long userId);

    UserApplication selectByApplicationId(Long applicationId);
}