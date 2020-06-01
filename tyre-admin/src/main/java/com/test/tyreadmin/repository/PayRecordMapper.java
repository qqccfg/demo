package com.test.tyreadmin.repository;

import com.test.tyreadmin.common.page.PageQueryBean;
import com.test.tyreadmin.entity.PayRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PayRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PayRecord record);

    int insertSelective(PayRecord record);

    PayRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PayRecord record);

    int updateByPrimaryKey(PayRecord record);

    Integer selectCountByUserId(Long userId);

    List<PayRecord> selectPageByUserId(Long userId, PageQueryBean pageQueryBean);
}