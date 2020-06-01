package com.test.tyrelocation.controller;

import com.test.tyrelocation.common.Exception.TyreException;
import com.test.tyrelocation.common.emum.ExceptionEnum;
import com.test.tyrelocation.common.emum.ResponseEnum;
import com.test.tyrelocation.common.response.ResponseRet;
import com.test.tyrelocation.service.InvocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Date: 2020/3/4 13:54
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@RequestMapping("invocation")
@Controller
public class InvocationController {

    @Autowired
    private InvocationService invocationService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostMapping("/oauth/token")
    @ResponseBody
    @CrossOrigin
    public ResponseRet<Map<String, String>> oauthToken(String APIKey, String secretKey){
        Map<String, String> result = invocationService.oauthToken(APIKey, secretKey);
        return new ResponseRet<>(ResponseEnum.OK, result);
    }

    @RequestMapping("/api/tyre/detection")
    @ResponseBody
    @CrossOrigin
    public ResponseRet<Map<String, Object>> tyreDetection(String data, String token
            , @RequestParam(value = "mode", defaultValue = "1") int mode){
        String userId = redisTemplate.opsForValue().get(token);
        redisTemplate.delete(token);
        if(!imageCheck(data)){
            throw new TyreException(ExceptionEnum.IMAGE_ERROR);
        }
        Map<String, Object> result = invocationService.tyreDetection(data, mode, userId);
        return new ResponseRet<>(ResponseEnum.OK, result);
    }

    @RequestMapping("/api/ocr/text")
    @ResponseBody
    public ResponseRet<Map<String, Object>> ocrText(String data, String token){
        String userId = redisTemplate.opsForValue().get(token);
        redisTemplate.delete(token);
        if(!imageCheck(data)){
            throw new TyreException(ExceptionEnum.IMAGE_ERROR);
        }
        Map<String, Object> result = invocationService.ocrText(data, userId);
        return new ResponseRet<>(ResponseEnum.OK, result);
    }

    @RequestMapping("/api/ocr/plateLicense")
    @ResponseBody
    public ResponseRet<Map<String, Object>> ocrPlateLicense(String data, String token){
        String userId = redisTemplate.opsForValue().get(token);
        redisTemplate.delete(token);
        if(!imageCheck(data)){
            throw new TyreException(ExceptionEnum.IMAGE_ERROR);
        }
        Map<String, Object> result = invocationService.ocrPlateLicense(data, userId);
        return new ResponseRet<>(ResponseEnum.OK, result);
    }

    private boolean imageCheck(String data){
        String suffix=data.substring(data.indexOf("/")+1, data.indexOf(";"));
        if (!("jpg".equals(suffix) || "png".equals(suffix) || "bmp".equals(suffix)|| "jpeg".equals(suffix))){
            return false;
        }
        int size0 = data.length();
        String tail = data.substring(size0-10);
        int equalIndex = tail.indexOf("=");
        if (equalIndex>0){
            size0 = size0 - (10-equalIndex)-22;
        }
        int kbSize = (int) (size0 - ((double)size0/8)*2)/1024;
        if (kbSize>1500){
            return false;
        }
        return true;
    }
}
