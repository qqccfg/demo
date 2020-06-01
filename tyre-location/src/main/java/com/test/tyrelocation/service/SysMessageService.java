package com.test.tyrelocation.service;

import com.test.tyrelocation.entity.Message;
import com.test.tyrelocation.entity.SysMessage;

import java.util.List;
import java.util.Map;

/**
 * @Date: 2019/11/28 14:58
 * @Author: JackLei
 * @Description:
 * @Version:
 */
public interface SysMessageService {

    int getMessageCount(Long userId);

    Map<String, List<SysMessage>> getMessageIndex(Long userId);
}
