package com.test.bike.bike.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @Author JackLei
 * @Date: Created in 2018/5/3 17:54
 * @Description
 */
@Data
public class BikeLocation {

    private String id;

    private Integer bikeNumber;

    private int status;

    private Double[] coordinates;

    private Double distance;

    private int type;


}
