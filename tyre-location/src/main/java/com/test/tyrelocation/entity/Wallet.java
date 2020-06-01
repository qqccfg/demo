package com.test.tyrelocation.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Wallet implements Serializable {
    private Long id;

    private Long userId;

    private BigDecimal balance;

    private BigDecimal invoice;

    private Date gmtCreate;

    private Date gmtModified;

    private static final long serialVersionUID = 1L;

}