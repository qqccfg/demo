package com.test.tyrelocation.service;

import com.test.tyrelocation.common.Exception.TyreException;
import com.test.tyrelocation.common.constant.Constants;
import com.test.tyrelocation.common.emum.ExceptionEnum;
import com.test.tyrelocation.common.emum.MessageEnum;
import com.test.tyrelocation.common.page.CategoryQueryBean;
import com.test.tyrelocation.common.page.PageQueryBean;
import com.test.tyrelocation.entity.Message;
import com.test.tyrelocation.repository.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @Date: 2019/11/28 15:44
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Service
public class MessageServiceImpl implements MessageService{

    private static final int UP = 1;    //上级
    private static final int BELOW = 2; //下级

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public int getMessageCount(Long userId) {
        CategoryQueryBean defaultBean = new CategoryQueryBean();
        defaultBean.setStatus(MessageEnum.MSG_STATUS_NORMAL.getCode());
        Integer count = messageMapper.selectByUserIdCount(userId, defaultBean);
        if (count==null){
            count = 0;
        }
        return count;
    }

    @Override
    public int getUnReadMessageCount(Long id) {
        CategoryQueryBean defaultBean = new CategoryQueryBean();
        defaultBean.setStatus(MessageEnum.MSG_STATUS_NORMAL.getCode());
        Integer count = messageMapper.selectByUserIdCount(id, defaultBean);
        if (count==null){
            count = 0;
        }
        return count;
    }

    @Override
    public PageQueryBean getMessages(Long userId, CategoryQueryBean queryBean) {
        PageQueryBean result = new PageQueryBean();
        Integer count = messageMapper.selectByUserIdCount(userId, queryBean);
        if (Objects.isNull(count)){
            return result;
        }
        queryBean.setTotalRows(count);
        List<Message> messages = messageMapper.selectByUserIdForPage(userId, queryBean);
        result.setCurrentPage(queryBean.getCurrentPage());
        result.setItems(messages);
        result.setTotalRows(count);
        return result;
    }

    @Override
    public void delete(Long userId, List<Long> msgIds) {
        messageMapper.updateBatchByPrimaryKeyToStatus(msgIds, MessageEnum.MSG_STATUS_DELETE.getCode());
    }

    @Override
    public void read(Long userId, List<Long> msgIds) {
        messageMapper.updateBatchByPrimaryKeyToStatus(msgIds, MessageEnum.MSG_STATUS_READ.getCode());
    }

    @Override
    public List<Message> detail(Long userId, Long id) {
        List<Message> result = new ArrayList<>();
        Message message = messageMapper.selectByIdAndUserId(userId, id);
        if (Objects.isNull(message)){
            throw new TyreException(ExceptionEnum.PARAMETER_ILLEGALITY);
        }
        Message message1 = messageMapper.selectRoundByPrimaryKey(userId, id, UP);
        Message message2 = messageMapper.selectRoundByPrimaryKey(userId, id, BELOW);
        result.add(Objects.isNull(message1) ? new Message() : message1);
        result.add(message);
        result.add(Objects.isNull(message2) ? new Message() : message2);
        read(userId, Arrays.asList(id));
        return result;
    }
}
