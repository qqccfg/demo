package com.test.tyrelocation.service;

import com.test.tyrelocation.common.Exception.TyreException;
import com.test.tyrelocation.common.emum.ConfigEnum;
import com.test.tyrelocation.common.emum.ExceptionEnum;
import com.test.tyrelocation.common.tool.Generator;
import com.test.tyrelocation.entity.Api;
import com.test.tyrelocation.entity.Application;
import com.test.tyrelocation.entity.ApplicationApi;
import com.test.tyrelocation.entity.UserApplication;
import com.test.tyrelocation.repository.ApiMapper;
import com.test.tyrelocation.repository.ApplicationMapper;
import com.test.tyrelocation.repository.ApplicationApiMapper;
import com.test.tyrelocation.repository.UserApplicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Date: 2019/10/8 14:04
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Service
public class ApplicationServiceImpl implements ApplicationService{

    @Autowired
    private ApplicationMapper applicationMapper;

    @Autowired
    private ApplicationApiMapper applicationApiMapper;

    @Autowired
    private ApiMapper apiMapper;

    @Autowired
    private UserApplicationMapper userApplicationMapper;

    @Override
    @Transactional
    public void create(Application application, Long userId) {
        Integer applicationNum = userApplicationMapper.selectApplicationNumByUserId(userId);
        if (Objects.nonNull(applicationNum)){
            if (applicationNum > ConfigEnum.APPLICATION_NUM.getCode()){
                throw new TyreException(ExceptionEnum.APPLICATION_IS_MAX);
            }
        }
        checkApi(application.getApis());
        Long id = Generator.getId();
        application.setId(id);
        String secretKey = Generator.getSecretKey();
        application.setApiKey(Generator.getApiKey(secretKey, id));
        application.setSecretKey(secretKey);
        applicationMapper.insertSelective(application);
        List<ApplicationApi> application_apis = new ArrayList<>();
        application.getApis().stream().map(Api::getId).forEach(aId ->{
            application_apis.add(new ApplicationApi(id,aId));
        });
        applicationApiMapper.insertBatch(application_apis);
        userApplicationMapper.insertSelective(new UserApplication(userId, id));
    }

    @Override
    public List<Application> queryAllByUserId(Long userId) {
        List<Application> applications = applicationMapper.selectByUserId(userId);
        return applications;
    }

    @Override
    public Application queryByApplicationId(Long userId, Long applicationId) {
        checkApplication(userId, applicationId);
        Application application = applicationMapper.selectByPrimaryKey(applicationId);
        List<Api> apis = apiMapper.selectByApplicationId(applicationId);
        application.setApis(apis);
        return application;
    }

    @Override
    @Transactional
    public void deleteById(Long userId, Long applicationId) {
        checkApplication(userId, applicationId);
        applicationMapper.deleteByPrimaryKey(applicationId);
        applicationApiMapper.deleteByApplicationId(applicationId);
        userApplicationMapper.deleteByApplicationId(applicationId);
    }

    @Override
    @Transactional
    public void updateByPrimaryKeySelective(Long userId, Application application) {
        checkApplication(userId, application.getId());
        checkApi(application.getApis());
        applicationMapper.updateByPrimaryKeySelective(application);
        applicationApiMapper.deleteByApplicationId(application.getId());
        List<ApplicationApi> application_apis = new ArrayList<>();
        application.getApis().stream().map(Api::getId).forEach(aId ->{
            application_apis.add(new ApplicationApi(application.getId(),aId));
        });
        applicationApiMapper.insertBatch(application_apis);
    }
    private void checkApi(List<Api> apis){
        List<Api> list = apiMapper.selectByIdBatch(apis);
        if (apis.size()!=list.size()){
            throw new TyreException(ExceptionEnum.API_ILLEGALITY);
        }
    }
    private void checkApplication(Long userId, Long applicationId){
        UserApplication userApplication = userApplicationMapper.selectByUIdAndAid(userId, applicationId);
        if (userApplication == null){
            throw new TyreException(ExceptionEnum.PARAMETER_ILLEGALITY);
        }
    }
}
