package com.test.bike.sms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.test.bike.common.constants.Constants;
import com.test.bike.common.utils.HttpUtil;
import com.test.bike.security.Md5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author JackLei
 * @Date: Created in 2018/4/30 23:55
 * @Description
 */
@Slf4j
@Service("miaoDiSendImpl")
public class MiaoDiSendImpl implements SmsSend{

    private static String operation = "/industrySMS/sendSMS";

    @Override
    public void sendSms(String phone, String tplId, String params) {
        try {
            String url= Constants.MDSMS_REST_URL+operation;
            Map<String,String> param=new HashMap<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String timestamp = sdf.format(new Date());
            String sig = Md5Utils.getMd5(Constants.ACCOUNT_SID +Constants.AUTH_TOKEN +timestamp);
            param.put("accountSid",Constants.ACCOUNT_SID);
            param.put("to",phone);
            param.put("templateid",tplId);
            param.put("param",params);
            param.put("timestamp",timestamp);
            param.put("sig",sig);
            param.put("respDataType","json");
            String result= HttpUtil.post(url,param);
            JSONObject jsonObject= JSON.parseObject(result);
            if(!jsonObject.getString("respCode").equals("00000")){
                log.error("fail to send sms to "+phone+":"+params+":"+result);
            }
        }catch (Exception e){
            log.error("fail to send sms to "+phone+":"+params,e);
        }

    }


}
