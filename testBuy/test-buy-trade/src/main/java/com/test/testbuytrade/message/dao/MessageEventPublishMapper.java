package com.test.testbuytrade.message.dao;

import com.test.testbuytrade.message.entity.MessageEventPublish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageEventPublishMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MessageEventPublish record);

    int insertSelective(MessageEventPublish record);

    MessageEventPublish selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MessageEventPublish record);

    int updateByPrimaryKey(MessageEventPublish record);
}