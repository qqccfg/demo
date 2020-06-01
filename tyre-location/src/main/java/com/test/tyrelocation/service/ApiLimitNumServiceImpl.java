package com.test.tyrelocation.service;

import com.test.tyrelocation.entity.ApiLimitNum;
import com.test.tyrelocation.repository.ApiLimitNumMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Date: 2020/3/15 10:39
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Service
public class ApiLimitNumServiceImpl implements ApiLimitNumService{

    @Autowired
    private ApiLimitNumMapper apiLimitNumMapper;

    @Override
    public List<Integer> queryByApiIds(List<Long> apiIds, Long userId) {
        List<Integer> result = new ArrayList<>();
        List<ApiLimitNum> apiLimitNums = apiLimitNumMapper.selectByUserId(userId);
        for (Long apiId : apiIds){
            List<Integer> limitNum = apiLimitNums.stream().filter((apiLimitNum) -> {
                return apiLimitNum.getApiId().equals(apiId);
            }).map(apiLimitNum -> apiLimitNum.getLimitNum())
                    .collect(Collectors.toList());
            result.add(limitNum.get(0));
        }
        return result;
    }

    @Override
    public Integer queryByApiId(Integer apiId) {
        ApiLimitNum apiLimitNum = apiLimitNumMapper.selectByApiId(apiId);
        return apiLimitNum.getLimitNum();
    }
}
