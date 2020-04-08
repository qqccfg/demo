package com.test.testbuystock.stock.service;

import com.test.testbuystock.common.constants.Constants;
import com.test.testbuystock.common.utils.RedisUtli;
import com.test.testbuystock.stock.dao.StockFlowMapper;
import com.test.testbuystock.stock.dao.StockMapper;
import com.test.testbuystock.stock.entity.Stock;
import com.test.testbuystock.stock.entity.StockFlow;
import com.test.testbuystock.stock.entity.StockReduce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JackLei
 * @Title: StockServiceImpl
 * @ProjectName testBuy
 * @Description:
 * @date 2018/6/2116:55
 **/
@Service
public class StockServiceImpl implements StockService{
    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisUtli redisUtli;

    @Autowired
    private StockFlowMapper stockFlowMapper;
    /**
     * @Author JackLei
     * @Date cteate in 2018/6/24 11:44
     * @Description 查询库存
    **/
    @Override
    public int queryStock(Long skuId) {
        //其实应该resis 中已经存在数据 直接从redis 中取数据的
        //先查 redis 中是否有值
        Integer stockCount=null;
        String stockKey=Constants.CACHE_PRODUCT_STOCK+":"+skuId;
        String stockLockKey=Constants.CACHE_PRODUCT_LOCK_STOCK+":"+skuId;
        Object stockobj=redisTemplate.opsForValue().get(stockKey);
        if (stockobj==null){
            Stock stock=stockMapper.selectBySkuId(skuId);
            stockCount=stock.getStock();
            redisUtli.skuStockInit(stockKey,stockLockKey,stock.getStock().toString(),stock.getLockStock().toString());
        }else {
            stockCount= Integer.valueOf(stockobj.toString());
        }



        return stockCount;
    }
    /**
     * @Author JackLei
     * @Date cteate in 2018/6/24 11:44
     * @Description 扣减库存
    **/
    @Override
    @Transactional
    public Map<Long, Integer> reduceStock(List<StockReduce> reduceList) {
        //以订单ID 加个缓存锁 防止程序短时间重试 重复扣减库存 不用解锁 自己超时
        Long orderNo = reduceList.get(0).getOrderNo();
        Boolean resultLock=redisUtli.distributeLock(Constants.ORDER_RETRY_LOCK,orderNo.toString(),3000);
        if (!resultLock){
            //失败 重复提交
            return Collections.EMPTY_MAP;
        }
        Map<Long,Integer> stockMap=new HashMap<>();
        reduceList.stream().forEach(param->{
            String stockKey=Constants.CACHE_PRODUCT_STOCK+param.getSkuId();
            String stockLockKey=Constants.CACHE_PRODUCT_LOCK_STOCK+param.getSkuId();
            Object result=redisUtli.reduceStock(stockKey,stockLockKey,param.getReduceCount().toString(), String.valueOf(Math.abs(param.getReduceCount())));
            if (result instanceof Long){
                //库存不足 或者不存在 扣减失败 做一个记录
                stockMap.put(param.getSkuId(),-1);
            }
            if (result instanceof List){
                //扣减成功 记录流水
                List resultList= (List) result;
                int stockChange=((Long)resultList.get(0)).intValue();
                int stockLockChange=((Long)resultList.get(1)).intValue();
                StockFlow stockFlow=new StockFlow();
                stockFlow.setSkuId(param.getSkuId());
                stockFlow.setOrderNo(param.getOrderNo());
                stockFlow.setStockBefore(param.getReduceCount()+stockChange);
                stockFlow.setStockAfter(stockChange);
                stockFlow.setStockChange(param.getReduceCount());
                stockFlow.setLockStockBefore(param.getReduceCount()+stockLockChange);
                stockFlow.setLockStockAfter(stockLockChange);
                stockFlow.setLockStockChange(param.getReduceCount());
                stockFlowMapper.insertSelective(stockFlow);
                stockMap.put(param.getSkuId(),1);
            }
        });
        return stockMap;
    }
}
