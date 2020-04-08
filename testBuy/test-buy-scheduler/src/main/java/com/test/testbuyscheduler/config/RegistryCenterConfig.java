package com.test.testbuyscheduler.config;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author JackLei
 * @Title: RegistryCenterConfig
 * @ProjectName testBuy
 * @Description:
 * @date 2018/6/2520:09
 **/
@Configuration
@ConditionalOnExpression("'${regCenter.serverList}'.length()>0")
public class RegistryCenterConfig {
    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter registryCenter(@Value("${regCenter.serverList}") final String serverList,@Value("${regCenter.namespace}") final String namespance){
        return new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverList,namespance));
    }

}
