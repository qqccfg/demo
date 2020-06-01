package com.test.tyrelocation.repository;

import com.test.tyrelocation.common.page.InvoicePageBean;
import com.test.tyrelocation.entity.Invoice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InvoiceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Invoice record);

    int insertSelective(Invoice record);

    Invoice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Invoice record);

    int updateByPrimaryKey(Invoice record);

    List<Invoice> selectByUserId(Long userId);

    Integer selectCountByUserIdAndStatus(Long userId, int status);

    List<Invoice> selectByUserIdAndStatus(Long userId, InvoicePageBean bean);

    Invoice selectByPrimaryKeyAndUserId(Long userId, Long id);
}