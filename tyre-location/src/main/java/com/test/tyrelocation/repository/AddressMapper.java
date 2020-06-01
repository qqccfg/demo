package com.test.tyrelocation.repository;

import com.test.tyrelocation.entity.Address;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Address record);

    int insertSelective(Address record);

    Address selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Address record);

    int updateByPrimaryKey(Address record);

    Address selectByPrimaryKeyAndUserId(Long userId, Long id);

    Integer selectCountByUserId(Long userId);

    List<Address> selectAllByUserId(Long userId);

    Address selectNowDefaultByUserId(Long userId);
}