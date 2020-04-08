package com.test.testbuytrade.trade.service;

import com.test.testbuytrade.common.enums.TradeStatus;
import com.test.testbuytrade.common.rese.ApiResult;
import com.test.testbuytrade.product.dao.ProductSkuMapper;
import com.test.testbuytrade.product.entity.ProductSku;
import com.test.testbuytrade.trade.dao.TradeItemMapper;
import com.test.testbuytrade.trade.dao.TradeMapper;
import com.test.testbuytrade.trade.entity.StockReduce;
import com.test.testbuytrade.trade.entity.Trade;
import com.test.testbuytrade.trade.entity.TradeItem;
import com.test.testbuytrade.trade.feign.KeyGeneratorServiceClient;
import com.test.testbuytrade.trade.feign.SockServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author JackLei
 * @Title: TradeServiceImpl
 * @ProjectName testBuy
 * @Description:
 * @date 2018/6/2113:57
 **/
@Service
public class TradeServiceImpl implements TradeService{

    @Autowired
    private KeyGeneratorServiceClient keygen;

    @Autowired
    private SockServiceClient sockServiceClient;

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private TradeMapper tradeMapper;

    @Autowired
    private TradeItemMapper tradeItemMapper;

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * @Author JackLei
     * @Date cteate in 2018/6/28 16:24
     * @Description 下单
    **/
    @Override
    public List<TradeItem> order(List<TradeItem> tradeItemList) {
        //获取订单id
        String orderNo=keygen.key();
        Long tradeNo=Long.parseLong(orderNo);
        Long userUUId=tradeItemList.get(0).getUserUuid();
        //库存
        List<StockReduce> stockReduceList=new ArrayList<>();
        tradeItemList.stream().forEach(
                param->{
                    StockReduce stockReduce=new StockReduce();
                    stockReduce.setOrderNo(tradeNo);
                    stockReduce.setReduceCount(param.getQuantity());
                    stockReduce.setSkuId(param.getSku().longValue());

                    stockReduceList.add(stockReduce);
                }
        );
        ApiResult<Map<Long,Integer>> stockRessult=sockServiceClient.reduceStock(stockReduceList);
        //判断逻辑 插入订单
        Map<Long,Integer> stockResultMap=stockRessult.getData();
        //查询相关sku的属性
        List<ProductSku> productSkuList=productSkuMapper.selectBySkuIdList(stockResultMap.keySet());
        //创建订单
        Trade trade=new Trade();
        trade.setTradeNo(tradeNo);
        trade.setUserUuid(userUUId);
        trade.setStatus(TradeStatus.WAITING_PAY.getValue());
        tradeMapper.insertSelective(trade);
        stockResultMap.keySet().stream().forEach(
                param->{
                    if (stockResultMap.get(param)==-1){
                       TradeItem tradeItem=tradeItemList.stream().filter(
                               item->item.getSku().longValue()==param
                       ).findFirst().get();
                       tradeItemList.remove(tradeItem);
                    }
                }
        );
        //计算价格
        tradeItemList.stream().forEach(
                param->{
                    ProductSku sku=productSkuList.stream().filter(
                            skuparam->param.getSku().longValue()==skuparam.getId()
                    ).findFirst().get();
                    param.setTradeNo(tradeNo);
                    param.setSkuImageUrl(sku.getImgUrl());
                    param.setCurrentPrice(sku.getSkuPrice().longValue());
                    param.setTotalPrice(sku.getSkuPrice()*param.getQuantity().longValue());

                    tradeItemMapper.insertSelective(param);
                }
        );
        redisTemplate.opsForValue().set(tradeNo.toString(),tradeNo.toString(),20,TimeUnit.SECONDS);

        return tradeItemList;
    }
}
