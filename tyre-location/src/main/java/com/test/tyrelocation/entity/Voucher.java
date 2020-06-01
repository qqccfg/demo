package com.test.tyrelocation.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Voucher implements Serializable {
    private Long id;

    private Long userId;

    private Long productId;

    private BigDecimal money;

    private Integer payType;

    private Date startTime;

    private Date endTime;

    private Integer status;

    private Date gmtCreate;

    private Date gmtModified;

    private static final long serialVersionUID = 1L;

}