package com.test.tyreadmin.service;

import com.test.tyreadmin.common.constant.Constants;
import com.test.tyreadmin.common.page.PageQueryBean;
import com.test.tyreadmin.entity.Message;
import com.test.tyreadmin.entity.SysMessage;
import com.test.tyreadmin.entity.User;
import com.test.tyreadmin.repository.MessageMapper;
import com.test.tyreadmin.repository.SysMessageMapper;
import com.test.tyreadmin.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Date: 2020/4/22 19:52
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Service
public class MessageServiceImpl implements MessageService{

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private SysMessageMapper sysMessageMapper;


    @Override
    public void sendSysMessage(SysMessage sysMessage) {
        sysMessageMapper.insertSelective(sysMessage);
    }

    @Override
    public void deleteSysMessage(Long id) {
        sysMessageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageQueryBean sysMessageList(PageQueryBean pageQueryBean) {
        Integer count = sysMessageMapper.selectCountAll();
        if (Objects.isNull(count)){
            count = 0;
        }
        pageQueryBean.setTotalRows(count);
        List<SysMessage> sysMessages = sysMessageMapper.selectPage(pageQueryBean);
        pageQueryBean.setItems(sysMessages);
        return pageQueryBean;
    }

    @Override
    public void sendMessage(Message message) {
        messageMapper.insertSelective(message);
    }

    @Override
    public SysMessage sysMsgInfo(Long id) {
        return sysMessageMapper.selectByPrimaryKey(id);
    }
}
