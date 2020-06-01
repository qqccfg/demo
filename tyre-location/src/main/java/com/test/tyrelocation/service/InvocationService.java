package com.test.tyrelocation.service;

import java.util.Map;

/**
 * @Date: 2020/3/4 13:58
 * @Author: JackLei
 * @Description:
 * @Version:
 */
public interface InvocationService {

    Map<String, String> oauthToken(String APIKey, String secretKey);


    Map<String, Object> tyreDetection(String data, int mode, String userId);

    Map<String, Object> ocrText(String data, String userId);

    Map<String, Object> ocrPlateLicense(String data, String userId);
}
