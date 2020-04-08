package com.test.testbuytrade.product.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class Property implements Serializable {
    private Long id;

    private Long categoryId;

    private String name;

    private Date createTime;

    private Date updateTime;


}