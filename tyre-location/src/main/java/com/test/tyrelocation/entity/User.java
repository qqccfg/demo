package com.test.tyrelocation.entity;

import com.test.tyrelocation.common.validation.constraints.Password;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
public class User implements Serializable {

    @Null(message = "id参数非法")
    private Long id;

    @NotNull(message = "用户名不能为空")
    private String username;

    @Password(message = "8-14字符且需包含数字,字母,特殊字符{!@#$%^&*}")
    private String password;

    @NotNull(message = "手机号码不能为空")
    private String mobile;

    @Null(message = "status参数非法")
    private Byte status;

    @Null(message = "gmtCreate参数非法")
    private Date gmtCreate;

    @Null(message = "getModified参数非法")
    private Date getModified;

    private static final long serialVersionUID = 1L;


}