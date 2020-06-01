package com.test.tyrelocation.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Invoice implements Serializable {
    @Null(message = "此【id】参数异常")
    private Long id;

    @NotNull(message = "发票抬头不能为空")
    private String invoiceTitle;

    @NotNull(message = "发票类型不能为空")
    private Integer invoiceType;

    @Null(message = "此【userId】参数异常")
    private Long userId;

    @NotNull(message = "申请余额不能为空")
    private BigDecimal money;

    private String note;

    @NotNull(message = "地址不能为空")
    private Long addressId;

    @Null(message = "此【status】参数异常")
    private Integer status;

    @Null(message = "此【gmtCreate】参数异常")
    private Date gmtCreate;

    @Null(message = "此【gmtModified】参数异常")
    private Date gmtModified;

    private static final long serialVersionUID = 1L;


}