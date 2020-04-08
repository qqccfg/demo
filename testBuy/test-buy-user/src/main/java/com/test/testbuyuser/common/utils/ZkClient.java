package com.test.testbuyuser.common.utils;

import com.test.testbuyuser.common.constants.Parameters;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author JackLei
 * @Date: Created in 2018/6/13 13:38
 * @Description
 */
@Component
public class ZkClient {
    @Autowired
    private Parameters parameters;

    @Bean
    public CuratorFramework getZKClient(){
        CuratorFrameworkFactory.Builder builder=CuratorFrameworkFactory.builder()
                .connectString(parameters.getZkHost())
                .connectionTimeoutMs(3000)
                .retryPolicy(new RetryNTimes(5,10));
        CuratorFramework curatorFramework=builder.build();
        curatorFramework.start();
        return curatorFramework;
    }
}
