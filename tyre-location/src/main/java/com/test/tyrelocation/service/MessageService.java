package com.test.tyrelocation.service;

import com.test.tyrelocation.common.page.CategoryQueryBean;
import com.test.tyrelocation.common.page.PageQueryBean;
import com.test.tyrelocation.entity.Message;

import java.util.List;

/**
 * @Date: 2019/11/28 15:43
 * @Author: JackLei
 * @Description:
 * @Version:
 */
public interface MessageService {

    int getMessageCount(Long userId);

    int getUnReadMessageCount(Long id);

    PageQueryBean getMessages(Long userId, CategoryQueryBean queryBean);

    void delete(Long userId, List<Long> msgIds);

    void read(Long userId, List<Long> msgIds);

    List<Message> detail(Long userId, Long id);
}
