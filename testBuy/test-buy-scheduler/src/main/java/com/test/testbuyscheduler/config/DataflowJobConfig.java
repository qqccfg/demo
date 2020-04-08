package com.test.testbuyscheduler.config;

import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author JackLei
 * @Title: DataflowJobConfig
 * @ProjectName testBuy
 * @Description:
 * @date 2018/6/2520:06
 **/
@Configuration
public class DataflowJobConfig {
    @Bean(name = "stockDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.stock")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }
}
