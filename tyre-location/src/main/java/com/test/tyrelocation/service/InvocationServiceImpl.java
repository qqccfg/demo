package com.test.tyrelocation.service;

import com.test.tyrelocation.common.Exception.TyreException;
import com.test.tyrelocation.common.constant.Constants;
import com.test.tyrelocation.common.emum.ConfigEnum;
import com.test.tyrelocation.common.emum.ExceptionEnum;
import com.test.tyrelocation.common.tool.AESUtil;
import com.test.tyrelocation.common.tool.BaiduSdk;
import com.test.tyrelocation.common.tool.RedisUtils;
import com.test.tyrelocation.entity.Api;
import com.test.tyrelocation.entity.ApiLimitNum;
import com.test.tyrelocation.entity.Application;
import com.test.tyrelocation.entity.UserApplication;
import com.test.tyrelocation.opencv.ImgDetecation;
import com.test.tyrelocation.opencv.TensorFlowDetection;
import com.test.tyrelocation.repository.ApiLimitNumMapper;
import com.test.tyrelocation.repository.ApiMapper;
import com.test.tyrelocation.repository.ApplicationMapper;
import com.test.tyrelocation.repository.UserApplicationMapper;
import org.bouncycastle.jcajce.provider.symmetric.AES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Date: 2020/3/4 13:59
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Service
public class InvocationServiceImpl implements InvocationService{

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserApplicationMapper userApplicationMapper;

    @Autowired
    private ApplicationMapper applicationMapper;

    @Autowired
    private ApiLimitNumMapper apiLimitNumMapper;

    @Autowired
    private ApiMapper apiMapper;

    @Override
    public Map<String, String> oauthToken(String APIKey, String secretKey) {
        Map<String, String> result = new HashMap<>();
        String decodeVal = null;
        try {
            decodeVal = AESUtil.AESDecode(secretKey, APIKey);
        } catch (Exception e) {
            throw new TyreException(ExceptionEnum.OAUTH_TOKEN_ERROR);
        }
        Application application = applicationMapper.selectByPrimaryKey(Long.valueOf(decodeVal));
        if (Objects.isNull(application)){
            throw new TyreException(ExceptionEnum.OAUTH_TOKEN_ERROR);
        }
        UserApplication userApplication = userApplicationMapper.selectByApplicationId(application.getId());
        if (Objects.isNull(userApplication)){
            throw new TyreException(ExceptionEnum.OAUTH_TOKEN_ERROR);
        }
        String accessToken = redisUtils.saveOauthToken(String.valueOf(userApplication.getUserId()));
        result.put("access_token", accessToken);
        String expiresIn = String.valueOf(TimeUnit.MINUTES.toSeconds(ConfigEnum.TOKEN_TIMEOUT.getCode()));
        result.put("expires_in", expiresIn);
        return result;
    }

    @Override
    @Transactional
    public Map<String, Object> tyreDetection(String data, int mode, String userId) {
        Map<String, Object> result = new HashMap<>();
        if (Objects.isNull(userId)){
            throw new TyreException(ExceptionEnum.TOKEN_ERROR);
        }
        ApiLimitNum apiLimitNum = apiLimitNumMapper.selectByUserIdAndApiId(Long.valueOf(userId), Constants.TYRE_API_ID);
        if (apiLimitNum.getLimitNum() < 0){
            throw new TyreException(ExceptionEnum.API_NUMBER_EMPTY);
        }
        apiLimitNum.setLimitNum(apiLimitNum.getLimitNum()-1);
        apiLimitNumMapper.updateByPrimaryKeySelective(apiLimitNum);
        Api api = apiMapper.selectByPrimaryKey(Constants.TYRE_API_ID);
        if (api.getApiStatus()==Constants.UNABLE){
            throw new TyreException(ExceptionEnum.API_UNABLE);
        }
        String dataSub = data.substring(data.indexOf(",")+1);
        byte[] dataByteArray = Base64Utils.decodeFromString(dataSub);
        FileOutputStream outputStream = null;
        try{
            outputStream = new FileOutputStream(Constants.IMAGE_DIR);
            outputStream.write(dataByteArray);
        }catch (Exception e) {
            throw new TyreException(ExceptionEnum.UNKNOWN);
        }
        if (mode==1){
            List<double[]> coords = ImgDetecation.detectionApi(Constants.IMAGE_DIR);
            result.put("name", "tyre");
            result.put("results", coords);
        }else if(mode==2){
            List<List<Integer>> results = ImgDetecation.detectionApiByBaiDu(dataSub);
            result.put("name", "tyre");
            result.put("results", results);
        }else if (mode==3){
            List<List<Float>> coords = TensorFlowDetection.detection(dataSub);
            result.put("name", "tyre");
            result.put("results", coords);
        }
        return result;
    }

    @Override
    public Map<String, Object> ocrText(String data, String userId) {
        Map<String, Object> result = new HashMap<>();
        if (Objects.isNull(userId)){
            throw new TyreException(ExceptionEnum.TOKEN_ERROR);
        }
        ApiLimitNum apiLimitNum = apiLimitNumMapper.selectByUserIdAndApiId(Long.valueOf(userId), Constants.OCR_TEXT_API_ID);
        if (apiLimitNum.getLimitNum() < 0){
            throw new TyreException(ExceptionEnum.API_NUMBER_EMPTY);
        }
        apiLimitNum.setLimitNum(apiLimitNum.getLimitNum()-1);
        apiLimitNumMapper.updateByPrimaryKeySelective(apiLimitNum);
        Api api = apiMapper.selectByPrimaryKey(Constants.OCR_TEXT_API_ID);
        if (api.getApiStatus()==Constants.UNABLE){
            throw new TyreException(ExceptionEnum.API_UNABLE);
        }
        String dataSub = data.substring(data.indexOf(",")+1);
        byte[] dataByteArray = Base64Utils.decodeFromString(dataSub);
        String words = BaiduSdk.textOcr(dataByteArray);
        result.put("result", words);
        return result;
    }

    @Override
    public Map<String, Object> ocrPlateLicense(String data, String userId) {
        Map<String, Object> result = new HashMap<>();
        if (Objects.isNull(userId)){
            throw new TyreException(ExceptionEnum.TOKEN_ERROR);
        }
        ApiLimitNum apiLimitNum = apiLimitNumMapper.selectByUserIdAndApiId(Long.valueOf(userId), Constants.OCR_PLATE_LICENSE_API_ID);
        if (apiLimitNum.getLimitNum() < 0){
            throw new TyreException(ExceptionEnum.API_NUMBER_EMPTY);
        }
        apiLimitNum.setLimitNum(apiLimitNum.getLimitNum()-1);
        apiLimitNumMapper.updateByPrimaryKeySelective(apiLimitNum);
        Api api = apiMapper.selectByPrimaryKey(Constants.OCR_PLATE_LICENSE_API_ID);
        if (api.getApiStatus()==Constants.UNABLE){
            throw new TyreException(ExceptionEnum.API_UNABLE);
        }
        String dataSub = data.substring(data.indexOf(",")+1);
        byte[] dataByteArray = Base64Utils.decodeFromString(dataSub);
        String words = BaiduSdk.plateLicense(dataByteArray);
        result.put("result", words);
        return result;
    }
}
