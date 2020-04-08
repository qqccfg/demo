package com.test.bike.fee.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Fee implements Serializable {
    private Long id;

    private Integer minUnit;

    private BigDecimal fee;

    private Byte bikeType;


}