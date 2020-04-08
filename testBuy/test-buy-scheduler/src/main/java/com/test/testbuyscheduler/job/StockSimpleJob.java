package com.test.testbuyscheduler.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * @author JackLei
 * @Title: StockSimpleJob
 * @ProjectName testBuy
 * @Description:
 * @date 2018/6/2816:01
 **/
public class StockSimpleJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println(String.format("tread id:%s,任务总片数：%s,当前分片项：%s",
                Thread.currentThread().getId(),shardingContext.getShardingTotalCount(),shardingContext.getShardingItem()));
        /**
         *
         * SELECT * FROM user WHERE status = 0 AND MOD(id, shardingTotalCount) = shardingItem
         */
    }
}
