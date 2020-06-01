package com.test.tyrelocation.service;

import com.test.tyrelocation.entity.Api;
import com.test.tyrelocation.repository.ApiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Date: 2019/10/29 21:05
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Service
public class ApiServiceImpl implements ApiService {

    @Autowired
    private ApiMapper apiMapper;

    @Override
    public List<Api> queryAll() {
        return apiMapper.selectAll();
    }

    @Override
    public List<Api> queryByApplicationId(Long applicationId) {
        return apiMapper.selectByApplicationId(applicationId);
    }

    @Override
    public String queryName(Long apiId) {
        return apiMapper.selectByPrimaryKey(apiId).getApiName();
    }
}
