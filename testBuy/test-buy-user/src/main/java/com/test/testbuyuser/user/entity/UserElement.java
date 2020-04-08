package com.test.testbuyuser.user.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author JackLei
 * @Date: Created in 2018/6/13 11:51
 * @Description 用于储存在redis的user   就是些用户的基本信息
 */
@Data
public class UserElement implements Serializable{
    private Long userId;

    private Long uuId;

    private String email;

    private String nackname;

    private Byte status;
}
