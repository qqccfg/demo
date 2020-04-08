package com.test.bike.user.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class User implements Serializable {
    private Long id;

    private String nickname;

    private String mobile;

    private String headImg;

    private Byte verifyFlag;

    private Byte enableFlag;




}