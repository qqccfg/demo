package com.test.tyrelocation.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Api implements Serializable {
    private Long id;

    private String apiName;

    private Integer apiType;

    private String apiUrl;

    private Integer apiLimit;

    private Byte apiStatus;

    private Date gmtCreate;

    private Date gmtModified;

    private static final long serialVersionUID = 1L;

}