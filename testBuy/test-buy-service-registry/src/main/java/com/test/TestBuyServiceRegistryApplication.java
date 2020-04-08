package com.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author JackLei
 * @Date: Created in 2018/6/9 15:51
 * @Description
 */
@SpringBootApplication
@EnableEurekaServer
public class TestBuyServiceRegistryApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestBuyServiceRegistryApplication.class,args);
    }
}
