package com.test.testbuytrade.product.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Product implements Serializable {
    private Long id;

    private Long categoryId;

    private Long brandId;

    private String productName;

    private Integer price;

    private Byte status;

    private Date createTime;

    private Date update;

    private List<ProductSku> skuList;


}