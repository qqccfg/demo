package com.test.tyreadmin.repository;

import com.test.tyreadmin.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectAll();

    int updateStatusById(Long id, @Param("status") int status);

    List<User> selectByStatus(int status);

    User selectByMobile(String mobile);
}