package com.test.testbuyuser.message.dao;

import com.test.testbuyuser.message.entity.MessageEventProcess;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageEventProcessMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MessageEventProcess record);

    int insertSelective(MessageEventProcess record);

    MessageEventProcess selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MessageEventProcess record);

    int updateByPrimaryKey(MessageEventProcess record);
}