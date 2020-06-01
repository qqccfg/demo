package com.test.tyrelocation;

import com.test.tyrelocation.repository.OrderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TyreLocationApplicationTests {

    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void contextLoads() {
        Integer sum = orderMapper.selectSumByYMD("17", "%y",1L);
        System.out.println(sum);
    }

}
