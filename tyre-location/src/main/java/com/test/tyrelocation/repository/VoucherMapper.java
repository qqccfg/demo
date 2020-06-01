package com.test.tyrelocation.repository;

import com.test.tyrelocation.common.page.VoucherPageBean;
import com.test.tyrelocation.entity.Voucher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VoucherMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Voucher record);

    int insertSelective(Voucher record);

    Voucher selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Voucher record);

    int updateByPrimaryKey(Voucher record);

    List<Voucher> selectPageByUserId(@Param("voucherPageBean") VoucherPageBean voucherPageBean, @Param("userId") Long userId);

    Integer selectCountByUserIdAndStatus(int status, Long userId);

    List<Voucher> selectByUserIdAndEnable(Long userId);
}