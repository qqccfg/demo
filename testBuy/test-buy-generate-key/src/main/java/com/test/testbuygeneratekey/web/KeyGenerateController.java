package com.test.testbuygeneratekey.web;

import com.test.testbuygeneratekey.keygen.SnowFlakeKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JackLei
 * @Title: KeyGenerateController
 * @ProjectName testBuy
 * @Description:
 * @date 2018/6/2821:14
 **/
@RestController
@RequestMapping
public class KeyGenerateController {
    @Autowired
    private SnowFlakeKeyGenerator snowFlakeKeyGenerator;
    @RequestMapping("/generatorkey")
    public String key(){
        return String.valueOf(snowFlakeKeyGenerator.generatorKey().longValue());
    }
}
