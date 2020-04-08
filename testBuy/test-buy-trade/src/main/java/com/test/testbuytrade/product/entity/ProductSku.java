package com.test.testbuytrade.product.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class ProductSku implements Serializable {
    private Long id;

    private Long spuId;

    private String skuName;

    private Integer skuPrice;

    private String imgUrl;

    private Byte enableFlag;

    private Date createTime;

    private Date updateTime;

    private List<SpuPropertyOption> skuPropertyOptions;


}