package com.test.bike.record.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class Record implements Serializable {
    private Long id;

    private Long userId;

    private Long bikeNum;

    private String recordNo;

    private Date startTime;

    private Date endTime;

    private Integer rideTime;

    private BigDecimal rideCost;

    private Byte status;


}