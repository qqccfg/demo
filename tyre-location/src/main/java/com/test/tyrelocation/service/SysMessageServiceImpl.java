package com.test.tyrelocation.service;

import com.test.tyrelocation.common.emum.MessageEnum;
import com.test.tyrelocation.entity.Message;
import com.test.tyrelocation.entity.SysMessage;
import com.test.tyrelocation.repository.SysMessageMapper;
import com.test.tyrelocation.repository.SysMessageUserDeleteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Date: 2019/11/28 14:58
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Service
public class SysMessageServiceImpl implements SysMessageService{

    @Autowired
    private SysMessageMapper sysMessageMapper;


    @Override
    public int getMessageCount(Long userId) {
        Integer count = sysMessageMapper.selectByUserIdCount(userId);
        if (count==null){
            count=0;
        }
        return count;
    }

    @Override
    public Map<String, List<SysMessage>> getMessageIndex(Long userId) {
        Map<String, List<SysMessage>> result = new HashMap<>();
        List<SysMessage> sysMessages = sysMessageMapper.selectByUserId(userId);
        Map<Integer, List<SysMessage>> sysMessageMap = sysMessages.stream().collect(Collectors.groupingBy(SysMessage::getCategory));
        for (Integer index : sysMessageMap.keySet()){
            switch (index){
                case  1 :
                    result.put(MessageEnum.SYS_NOTICE.getMessage(), sysMessageMap.get(index).stream().sorted((t1, t2)->{
                                return t1.getId()<t2.getId()?1:-1;
                            }).
                            limit(5).collect(Collectors.toList()));
                    break;
                case 2 :
                    result.put(MessageEnum.SYS_UPGRADE.getMessage(), sysMessageMap.get(index).stream().sorted((t1, t2)->{
                        return t1.getId()<t2.getId()?1:-1;
                    }).limit(5).collect(Collectors.toList()));
                    break;
                case 3 :
                    result.put(MessageEnum.SYS_NEW_PRODUCT.getMessage(), sysMessageMap.get(index).stream().sorted((t1, t2)->{
                        return t1.getId()<t2.getId()?1:-1;
                    }).limit(5).collect(Collectors.toList()));
                    break;
                case 4 :
                    result.put(MessageEnum.SYS_MSG_OTHER.getMessage(), sysMessageMap.get(index).stream().sorted((t1, t2)->{
                        return t1.getId()<t2.getId()?1:-1;
                    }).limit(5).collect(Collectors.toList()));
                    break;
            }
        }
        return result;
    }
}
