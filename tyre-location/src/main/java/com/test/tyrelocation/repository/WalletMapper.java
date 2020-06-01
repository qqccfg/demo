package com.test.tyrelocation.repository;

import com.test.tyrelocation.entity.Wallet;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WalletMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Wallet record);

    int insertSelective(Wallet record);

    Wallet selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Wallet record);

    int updateByPrimaryKey(Wallet record);

    Wallet selectByUserId(Long userId);
}