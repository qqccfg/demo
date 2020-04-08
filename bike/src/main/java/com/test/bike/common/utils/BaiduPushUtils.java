package com.test.bike.common.utils;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.baidu.yun.push.model.PushMsgToSingleDeviceResponse;
import com.test.bike.common.constants.Constants;
import com.test.bike.user.entity.UserElement;

/**
 * @Author JackLei
 * @Date: Created in 2018/5/7 20:16
 * @Description
 */
public class BaiduPushUtils {

    public static void pushMsgToSingleDevice (UserElement ue, String massage) throws PushClientException, PushServerException {
        String result=null;
        PushKeyPair pair = new PushKeyPair(Constants.BAIDUYUN_APIKEY,Constants.BAIDUYUN_SECRETKEY);
        BaiduPushClient pushClient = new BaiduPushClient(pair, Constants.BAIDUYUN_URL);

        try {
            // 4. 设置请求参数，创建请求实例
            PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest().
                    addChannelId(ue.getPushChannelId()).
                    addMsgExpires(new Integer(3600)).   //设置消息的有效时间,单位秒,默认3600*5.
                    addMessageType(1)              //设置消息类型,0表示透传消息,1表示通知,默认为0.
                    .addMessage(massage);
            if ("android".equals(ue.getPlatform())){
                request.addDeviceType(3);
            }else if ("iso".equals(ue.getPlatform())){
                request.addDeviceType(4);
            }

            //设置设备类型，deviceType => 1 for web, 2 for pc,
            //3 for android, 4 for ios, 5 for wp.
            // 5. 执行Http请求
            PushMsgToSingleDeviceResponse response = pushClient.pushMsgToSingleDevice(request);
            // 6. Http请求返回值解析

        } catch (PushClientException e) {
            //ERROROPTTYPE 用于设置异常的处理方式 -- 抛出异常和捕获异常,
            //'true' 表示抛出, 'false' 表示捕获。
            if (BaiduPushConstants.ERROROPTTYPE) {
                throw e;
            } else {
                e.printStackTrace();
            }
        } catch (PushServerException e) {
            if (BaiduPushConstants.ERROROPTTYPE) {
                throw e;
            }
        }


    }
}
