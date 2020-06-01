package com.test.tyrelocation.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;

/**
 * @Date: 2019/11/19 10:09
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Data
public class UserElement extends User implements Serializable {

    @NotNull(message = "验证码不能为空")
    private String verifyCode;

    public UserElement(String verifyCode, @Null(message = "id参数非法") Long id, @NotNull(message = "用户名不能为空") String username, @NotNull(message = "密码不能为空") String password, @NotNull(message = "手机号码不能为空") String mobile, @Null(message = "status参数非法") Byte status, @Null(message = "gmtCreate参数非法") Date gmtCreate, @Null(message = "getModified参数非法") Date getModified) {
        super(id, username, password, mobile, status, gmtCreate, getModified);
        this.verifyCode = verifyCode;
    }

    private static final long serialVersionUID = 1L;
}
