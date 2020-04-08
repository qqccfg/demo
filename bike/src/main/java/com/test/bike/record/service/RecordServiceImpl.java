package com.test.bike.record.service;

import com.test.bike.record.dao.RecordMapper;
import com.test.bike.record.entity.Record;
import com.test.bike.record.entity.RideContrail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author JackLei
 * @Date: Created in 2018/5/8 13:53
 * @Description
 */
@Slf4j
@Service
public class RecordServiceImpl implements RecordService{

    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
    *@Author JackLei
    *@Date: 2018/5/8 23:41
    *@Description: 查询骑行历史订单记录
    */
    @Override
    public List<Record> radeHisoryRecord(Long userId, Long id) {
        List<Record> result=recordMapper.selectList(userId,id);
        return result;
    }

}
