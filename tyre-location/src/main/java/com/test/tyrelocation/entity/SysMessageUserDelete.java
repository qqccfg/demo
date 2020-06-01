package com.test.tyrelocation.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysMessageUserDelete implements Serializable {
    private Long id;

    private Long userId;

    private Long sysMessageId;

    private Date gmtCreate;

    private Date gmtModified;

    private static final long serialVersionUID = 1L;


}