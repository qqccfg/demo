package com.test.tyrelocation.repository;

import com.test.tyrelocation.entity.SysMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMessageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysMessage record);

    int insertSelective(SysMessage record);

    SysMessage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysMessage record);

    int updateByPrimaryKey(SysMessage record);

    Integer selectByUserIdCount(Long userId);

    List<SysMessage> selectByUserId(Long userId);
}