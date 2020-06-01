package com.test.tyrelocation.service;

import java.util.List;

/**
 * @Date: 2020/3/15 10:35
 * @Author: JackLei
 * @Description:
 * @Version:
 */
public interface ApiLimitNumService {

    List<Integer> queryByApiIds(List<Long> apiIds, Long userId);

    Integer queryByApiId(Integer apiId);
}
