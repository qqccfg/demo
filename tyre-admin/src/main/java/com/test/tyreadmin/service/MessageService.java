package com.test.tyreadmin.service;

import com.test.tyreadmin.common.page.PageQueryBean;
import com.test.tyreadmin.entity.Message;
import com.test.tyreadmin.entity.SysMessage;

/**
 * @Date: 2020/4/22 19:52
 * @Author: JackLei
 * @Description:
 * @Version:
 */
public interface MessageService {

    void sendSysMessage(SysMessage sysMessage);

    void deleteSysMessage(Long id);

    PageQueryBean sysMessageList(PageQueryBean pageQueryBean);

    void sendMessage(Message message);

    SysMessage sysMsgInfo(Long id);
}
