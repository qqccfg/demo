package com.test.user.entity;

import lombok.Data;

import java.io.Serializable;


@Data
public class UserElement implements Serializable{

    private Long userId;

    private Long uuid;

    private String email;

    private  String nickname;


}
