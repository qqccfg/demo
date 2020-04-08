package com.test.testbuytrade.trade.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author JackLei
 * @Title: KeyGeneratorServiceClient
 * @ProjectName testBuy
 * @Description:
 * @date 2018/6/2821:24
 **/
@FeignClient(name = "key-generator",fallback = KeyGeneratorServiceFallbake.class)
public interface KeyGeneratorServiceClient {
    @RequestMapping("/generatorkey")
    String key();
}
