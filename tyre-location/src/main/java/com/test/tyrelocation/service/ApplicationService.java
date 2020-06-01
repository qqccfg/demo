package com.test.tyrelocation.service;

import com.test.tyrelocation.entity.Application;

import java.util.List;

/**
 * @Date: 2019/10/8 14:04
 * @Author: JackLei
 * @Description: 应用操作接口
 * @Version:
 */
public interface ApplicationService {
    void create(Application application, Long userId);

    List<Application> queryAllByUserId(Long userId);

    Application queryByApplicationId(Long UserId, Long applicationId);

    void deleteById(Long userId, Long applicationId);

    void updateByPrimaryKeySelective(Long userId, Application application);
}
