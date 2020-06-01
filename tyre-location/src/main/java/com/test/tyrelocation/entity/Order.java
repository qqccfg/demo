package com.test.tyrelocation.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Order implements Serializable {
    private Long id;

    private Long userId;

    private Long productId;

    private Integer orderType;

    private BigDecimal money;

    private Date orderTime;

    private Integer status;

    private Date payTime;

    private Date gmtCreate;

    private Date gmtModified;

    private static final long serialVersionUID = 1L;


}