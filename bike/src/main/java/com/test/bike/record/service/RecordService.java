package com.test.bike.record.service;

import com.test.bike.record.entity.Record;
import com.test.bike.record.entity.RideContrail;

import java.util.List;

/**
 * @Author JackLei
 * @Date: Created in 2018/5/8 13:52
 * @Description
 */
public interface RecordService {
    List<Record> radeHisoryRecord(Long UserId,Long id);


}
