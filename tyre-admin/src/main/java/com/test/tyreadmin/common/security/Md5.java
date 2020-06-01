package com.test.tyreadmin.common.security;

import org.springframework.util.DigestUtils;

/**
 * @Date: 2019/11/11 13:50
 * @Author: JackLei
 * @Description:
 * @Version:
 */
public class Md5 {
    public static String encoding(String value){
        return DigestUtils.md5DigestAsHex(value.getBytes());
    }

    public static void main(String[] args) {
        System.out.println(encoding("123456"));
    }
}
