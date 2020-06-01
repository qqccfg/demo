package com.test.tyrelocation.service;

import com.test.tyrelocation.common.page.PageQueryBean;
import com.test.tyrelocation.entity.PayRecord;

import java.util.List;

/**
 * @Date: 2020/4/23 19:35
 * @Author: JackLei
 * @Description:
 * @Version:
 */
public interface PayRecordService {

    void createPayRecord(PayRecord payRecord);

    PageQueryBean getPayRecordListByUserId(Long userId, PageQueryBean pageQueryBean);

}
