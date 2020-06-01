package com.test.tyrelocation.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysMessage implements Serializable {
    private Long id;

    private Integer category;

    private String title;

    private String body;

    private Integer status;

    private Date gmtCreate;

    private Date gmtModified;

    private static final long serialVersionUID = 1L;


}