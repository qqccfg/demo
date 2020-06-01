package com.test.tyreadmin.repository;

import com.test.tyreadmin.common.page.InvoicePageBean;
import com.test.tyreadmin.entity.Invoice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InvoiceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Invoice record);

    int insertSelective(Invoice record);

    Invoice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Invoice record);

    int updateByPrimaryKey(Invoice record);

    Integer selectCountByStatus(int status);

    List<Invoice> selectPageByStatus(@Param("pageBean") InvoicePageBean pageBean);
}