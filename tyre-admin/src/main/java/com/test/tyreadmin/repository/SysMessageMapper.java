package com.test.tyreadmin.repository;

import com.test.tyreadmin.common.page.PageQueryBean;
import com.test.tyreadmin.entity.SysMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysMessageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysMessage record);

    int insertSelective(SysMessage record);

    SysMessage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysMessage record);

    int updateByPrimaryKey(SysMessage record);

    Integer selectCountAll();

    List<SysMessage> selectPage(@Param("pageQueryBean") PageQueryBean pageQueryBean);
}