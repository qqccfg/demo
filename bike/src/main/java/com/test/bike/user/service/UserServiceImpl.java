package com.test.bike.user.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.test.bike.cache.CommonCacheUtil;
import com.test.bike.common.constants.Constants;
import com.test.bike.common.exception.TestBikeException;
import com.test.bike.common.utils.QiniuFileUploadUtils;
import com.test.bike.common.utils.RandomVercode;
import com.test.bike.jms.SmsProcess;
import com.test.bike.security.AESUtil;
import com.test.bike.security.Base64Util;
import com.test.bike.security.Md5Utils;
import com.test.bike.security.RSAUtil;
import com.test.bike.user.dao.UserMapper;
import com.test.bike.user.entity.User;
import com.test.bike.user.entity.UserElement;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTempQueue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.jms.Destination;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author JackLei
 * @Date: Created in 2018/4/24 22:01
 * @Description
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private static final String VERIFYCODE_PREFIX = "verify.code.";

    private static final String SMS_QUEUE = "sms.queue";

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommonCacheUtil commonCacheUtil;

    @Autowired
    private SmsProcess smsProcess;

    @Override
    public String loging(String data, String key) throws TestBikeException {
        //首先就是解密数据，获得手机号跟验证码，然后进行对比验证是否通过，然后根据一些唯一条件生成token进行返回
        String token=null;
        try{
            byte[] deKey=RSAUtil.decryptByPrivateKey(Base64Util.decode(key));
            String deData= AESUtil.decrypt(data,new String(deKey,"UTF-8"));
            if(deData==null){
                throw new  Exception();
            }
            JSONObject jsonObject=JSON.parseObject(deData);
            String mobile=jsonObject.getString("mobile");
            String code=jsonObject.getString("code");
            String platform = jsonObject.getString("platform");//机器类型
            String pushChannelId=jsonObject.getString("pushChannelId");
            if (StringUtils.isBlank(mobile)||StringUtils.isBlank(code)||StringUtils.isBlank(platform)||StringUtils.isBlank(pushChannelId)){
                throw new Exception();
            }
            //拿到所有的数据了，开始验证是否匹配
            /**ridis中取验证码进行验证**/
//            String verCode=commonCacheUtil.getCacheValue(VERIFYCODE_PREFIX+mobile);
            String verCode=commonCacheUtil.getCacheValue(mobile);
            User user=null;
            if (code.equals(verCode)){
                user=userMapper.selectBymoblie(mobile);
                if (user==null){
                    user=new User();
                    user.setMobile(mobile);
                    user.setNickname(mobile);
                    userMapper.insertSelective(user);
                }
            }else {
                throw new TestBikeException("手机验证码错误");
            }
            //生成 token

            try{
                token=this.generateToken(user);
            }catch (Exception e){
                log.error("fail to token",e);
            }
            UserElement ue=new UserElement();
            ue.setUserId(user.getId());
            ue.setMobile(user.getMobile());
            ue.setToken(token);
            ue.setPlatform(platform);
            commonCacheUtil.putTokenWhenLogin(ue);


        }catch (Exception e){
            log.error("数据解析错误",e);
            throw new TestBikeException("数据解析错误");
        }



       return token;
    }

    @Override
    public void modifNickname(User user) throws TestBikeException{
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void sendVercode(String mobile, String ip) throws TestBikeException {
        String vercode=RandomVercode.getLoginVercode();
        int result=commonCacheUtil.putVercode(VERIFYCODE_PREFIX+mobile,vercode,Constants.LODIN_VERCODE_TYPE,Constants.EXP_TIME,ip);
        if (result==1){
            log.info("手机验证码未过期");
            throw new TestBikeException("手机验证码未过期");
        }else if (result==2){
            log.info("今日发送验证码已达上限");
            throw new TestBikeException("今日发送验证码已达上限");
        }else if(result==3){
            log.info("此ip已达上限");
            throw new TestBikeException("此ip已达上限");
        }
        log.info("Sending verify code {} for phone {}",vercode,mobile);
        //验证码推送到消息队列
        Destination destination=new ActiveMQQueue(SMS_QUEUE);
        Map<String,String> map=new HashMap<>();
        map.put("moblie",mobile);
        map.put("tplId", Constants.MDSMS_VERCODE_TPLID);
        map.put("vercode",vercode);
        String massage=JSON.toJSONString(map);
        smsProcess.SmsSendtoQueue(destination,massage);
    }
    /**
    *@Author JackLei
    *@Date: 2018/5/1 19:28
    *@Description:  修改头像
    */
    @Override
    public String modifHeadportrait(long userId, MultipartFile file) throws TestBikeException {
        try{
            User user=userMapper.selectByPrimaryKey(userId);
            //调用七牛
            String img= QiniuFileUploadUtils.UploadImg(file);
            user.setHeadImg(img);
            userMapper.updateByPrimaryKeySelective(user);
           return Constants.QINIU_BUCKET_URL+"/"+img;
        }catch (Exception e){
            log.error("fail to service modifHeadportrait",e);
            throw new TestBikeException("头像上传失败");
        }


    }

    public String generateToken(User user){
        String source=user.getId()+":"+user.getMobile()+System.currentTimeMillis();
        return Md5Utils.getMd5(source);

    }
}
