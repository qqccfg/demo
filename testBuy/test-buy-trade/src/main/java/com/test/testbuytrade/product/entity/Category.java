package com.test.testbuytrade.product.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class Category implements Serializable {
    private Long id;

    private Long parentId;

    private String name;

    private Integer sortOrder;

    private Byte status;

    private Date createTime;

    private Date updateTime;


}