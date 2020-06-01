package com.test.tyrelocation.repository;

import com.test.tyrelocation.entity.SysMessageUserDelete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysMessageUserDeleteMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysMessageUserDelete record);

    int insertSelective(SysMessageUserDelete record);

    SysMessageUserDelete selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysMessageUserDelete record);

    int updateByPrimaryKey(SysMessageUserDelete record);
}