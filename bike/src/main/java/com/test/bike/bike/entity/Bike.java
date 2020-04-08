package com.test.bike.bike.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class Bike implements Serializable {
    private Long id;

    private Long number;

    private Byte type;

    private Byte enable;


}