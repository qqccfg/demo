package com.test.tyrelocation.service;

import com.test.tyrelocation.common.Exception.TyreException;
import com.test.tyrelocation.common.constant.Constants;
import com.test.tyrelocation.common.emum.ConfigEnum;
import com.test.tyrelocation.common.emum.ExceptionEnum;
import com.test.tyrelocation.common.secuity.Md5;
import com.test.tyrelocation.common.tool.RedisUtils;
import com.test.tyrelocation.entity.*;
import com.test.tyrelocation.repository.ApiLimitNumMapper;
import com.test.tyrelocation.repository.ApiMapper;
import com.test.tyrelocation.repository.UserMapper;
import com.test.tyrelocation.repository.WalletMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Date: 2019/11/11 14:10
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Service
public class UserServiceImpl implements UserService{


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WalletMapper walletMapper;

    @Autowired
    private ApiMapper apiMapper;

    @Autowired
    private ApiLimitNumMapper apiLimitNumMapper;


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public User queryByUsernameOrMobile(String username) {
        return userMapper.selectByUsernameOrMobile(username);
    }

    @Override
    public void sendVerification(String mobile,String type ,String ip) {
        String value = "666666";
        int val = redisUtils.saveVerifyCode(mobile, type, ip, value);
        if (val==1){
            throw new TyreException(ExceptionEnum.VERIFY_IP_LIMIT);
        }else if(val==2){
            throw new TyreException(ExceptionEnum.VERIFY_MOBILE_LIMIT);
        }else if (val==3){
            throw new TyreException(ExceptionEnum.VERIFY_MOBILE_EXIST);
        }
    }

    @Override
    @Transactional
    public void register(UserElement userElement) {
        String mobileKey = userElement.getMobile()+ Constants.VERIFY_CODE_REG;
        String code = redisTemplate.opsForValue().get(mobileKey);
        redisTemplate.delete(mobileKey);
        if (userElement.getVerifyCode().equals(code)){
            User userCheck = userMapper.selectByUsernameOrMobile(userElement.getUsername());
            User userCheck2 = userMapper.selectByUsernameOrMobile(userElement.getMobile());
            if (userCheck!=null){
                throw new TyreException(ExceptionEnum.USERNAME_EXIST);
            }else if (userCheck2!=null){
                throw new TyreException(ExceptionEnum.MOBILE_EXIST);
            }
            User user = User.builder().username(userElement.getUsername())
                    .password(Md5.encoding(userElement.getPassword()))
                    .mobile(userElement.getMobile()).build();
            userMapper.insertSelective(user);
            Wallet wallet = new Wallet();
            wallet.setUserId(user.getId());
            walletMapper.insertSelective(wallet);
            List<Api> apis = apiMapper.selectAll();
            apiLimitNumMapper.insertBatch(apis, user.getId());
        }else {
            throw new TyreException(ExceptionEnum.VERIFY_ERROR);
        }
    }

    @Override
    public Map<String, String> toMobile(String usmMob) {
        Map<String, String> data = new HashMap<>();
        User user = userMapper.selectByUsernameOrMobile(usmMob);
        if (user==null){
            throw new TyreException(ExceptionEnum.NO_USER);
        }
        String mobileKey = Md5.encoding(user.getMobile());
        redisTemplate.opsForValue().setIfAbsent(mobileKey, user.getMobile(), ConfigEnum.RETRIEVE_TIMEOUT.getCode(), TimeUnit.MINUTES);
        data.put("key", mobileKey);
        String hideMobile = new StringBuilder(user.getMobile()).replace(3, 7,"****").toString();
        data.put("mobile", hideMobile);
        return data;
    }

    @Override
    public void sendRetVerification(String key, String type, String ip) {
        String mobile = redisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(mobile)){
            throw new TyreException(ExceptionEnum.PARAMETER_ILLEGALITY);
        }
        String value = "666666";
        int val = redisUtils.saveVerifyCode(mobile, type, ip, value);
        if (val==1){
            throw new TyreException(ExceptionEnum.VERIFY_IP_LIMIT);
        }else if(val==2){
            throw new TyreException(ExceptionEnum.VERIFY_MOBILE_LIMIT);
        }else if (val==3){
            throw new TyreException(ExceptionEnum.VERIFY_MOBILE_EXIST);
        }
    }

    @Override
    public String isRetVerifyCode(String key, String verifyCode) {
        String mobile = redisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(mobile)){
            throw new TyreException(ExceptionEnum.PARAMETER_ILLEGALITY);
        }
        String mobileKey = mobile+Constants.VERIFY_CODE_RET;
        String code = redisTemplate.opsForValue().get(mobileKey);
        if (!verifyCode.equals(code)){
            throw new TyreException(ExceptionEnum.VERIFY_ERROR);
        }
        String callbackKey =Md5.encoding(mobileKey);
        User user = userMapper.selectByUsernameOrMobile(mobile);
        redisTemplate.opsForValue().setIfAbsent(callbackKey, user.getUsername(),ConfigEnum.RETRIEVE_TIMEOUT.getCode(), TimeUnit.MINUTES);
        return callbackKey;
    }

    @Override
    public void resetPassword(String key, String password) {
        String username = redisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(username)){
            throw new TyreException(ExceptionEnum.PARAMETER_ILLEGALITY);
        }
        User user = User.builder().username(username).password(Md5.encoding(password)).build();
        user.setPassword(Md5.encoding(password));
        userMapper.updateByMobileSelective(user);
    }

    @Override
    public String callbackCheck(String data) {
        String username = redisTemplate.opsForValue().get(data);
        if (StringUtils.isEmpty(username)){
            throw new TyreException(ExceptionEnum.PARAMETER_ILLEGALITY);
        }
        return username;
    }
}
