package com.test.testbuyuser.user.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;
@Data
public class User implements Serializable {
    private Long id;

    private Long uuid;

    @Email(message = "邮箱格式不正确")
    private String email;

    @NotBlank(message = "密码不能为空")
    private String password;

    private String nackname;

    private String mobile;

    private Date createTime;

    private Date updateTime;

    private Byte status;


}