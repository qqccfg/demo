package com.test.testbuyscheduler.config;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.test.testbuyscheduler.job.StockSimpleJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author JackLei
 * @Title: StockJobConfig
 * @ProjectName testBuy
 * @Description:
 * @date 2018/6/2520:09
 **/
@Configuration
public class StockJobConfig {
    @Autowired
    private RegistryCenterConfig registryCenterConfig;

    @Autowired
    private ZookeeperRegistryCenter reCenter;

    @Bean
    public SimpleJob stockJob(){
        return new StockSimpleJob();
    }

    @Bean(initMethod = "init")
    public JobScheduler SmipleJobScheduler(final SimpleJob simpleJob,@Value("${stockJob.cron}") final String cron,@Value("${stockJob.shardingTotalCount}") final int shardingTotalCount,
                                           @Value("${stockJob.shardingItemParameters}") final String shardingItemParameters){
        return new SpringJobScheduler(simpleJob,reCenter,getLiteJobConfiguration(simpleJob.getClass(),cron,shardingTotalCount,shardingItemParameters));
    }
    /**
     * @Author JackLei
     * @Date cteate in 2018/6/28 16:13
     * @Description 任务配置类
    **/
    private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass,
                                                         final String cron,
                                                         final int shardingTotalCount,
                                                         final String shardingItemParameters){


        return LiteJobConfiguration
                .newBuilder(
                        new SimpleJobConfiguration(
                                JobCoreConfiguration.newBuilder(
                                        jobClass.getName(),cron,shardingTotalCount)
                                        .shardingItemParameters(shardingItemParameters)
                                        .build()
                                ,jobClass.getCanonicalName()
                        )
                )
                .overwrite(true)
                .build();

    }


}
