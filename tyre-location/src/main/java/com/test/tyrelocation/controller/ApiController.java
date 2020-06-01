package com.test.tyrelocation.controller;

import com.test.tyrelocation.common.emum.ResponseEnum;
import com.test.tyrelocation.common.response.ResponseRet;
import com.test.tyrelocation.entity.Api;
import com.test.tyrelocation.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Date: 2019/10/29 21:52
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@RestController
@RequestMapping("/api")
public class ApiController  {

    @Autowired
    private ApiService apiService;

    @RequestMapping("/queryAll")
    public ResponseRet<List<Api>> queryAll(){
        ResponseRet<List<Api>> result = new ResponseRet<>(ResponseEnum.OK);
        List<Api> apis = apiService.queryAll();
        result.setData(apis);
        return result;
    }

    @RequestMapping("/queryByByApplication")
    public ResponseRet<List<Api>> queryByApplicationId(Long id){
        ResponseRet<List<Api>> result = new ResponseRet<>(ResponseEnum.OK);
        List<Api> apis = apiService.queryByApplicationId(id);
        result.setData(apis);
        return result;
    }
}
