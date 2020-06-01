package com.test.tyrelocation.repository;

import com.test.tyrelocation.common.page.PageQueryBean;
import com.test.tyrelocation.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);

    Integer selectByUserIdCount(Long userId, @Param("queryBean") PageQueryBean queryBean);

    List<Message> selectByUserIdForPage(@Param("userId") Long userId,
                                        @Param("queryBean") PageQueryBean queryBean);

    int updateBatchByPrimaryKeyToStatus(@Param("msgIds")List<Long> msgIds, Integer value);

    /**
     *
     * @param userId
     * @param id
     * @param flag  上级 : 1   下级 : 2
     * @return
     */
    Message selectRoundByPrimaryKey(Long userId, Long id, int flag);

    Message selectByIdAndUserId(Long userId, Long id);
}