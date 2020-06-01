package com.test.tyrelocation.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;

@Data
public class Address implements Serializable {

    @Null(message = "【id】 此参数异常")
    private Long id;

    @Null(message = "【userId】此参数异常")
    private Long userId;

    @NotNull(message = "收件人不能为空")
    private String relationName;

    @NotNull(message = "地址不能为空")
    private String mainZone;

    @NotNull(message = "地址不能为空")
    private String detailZone;

    private String mailCode;

    @NotNull(message = "联系方式不能为空")
    private String mobile;

    @Null(message = "【isDefault】此参数异常")
    private Integer isDefault;

    @Null(message = "【gmtCreate】此参数异常")
    private Date gmtCreate;

    @Null(message = "【gmtModified】此参数异常")
    private Date gmtModified;

    private static final long serialVersionUID = 1L;


}