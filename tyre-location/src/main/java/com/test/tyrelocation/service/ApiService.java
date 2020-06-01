package com.test.tyrelocation.service;

import com.test.tyrelocation.entity.Api;

import java.util.List;

/**
 * @Date: 2019/10/29 21:02
 * @Author: JackLei
 * @Description:
 * @Version:
 */
public interface ApiService {
    List<Api> queryAll();

    List<Api> queryByApplicationId(Long applicationId);

    String queryName(Long apiId);
}
