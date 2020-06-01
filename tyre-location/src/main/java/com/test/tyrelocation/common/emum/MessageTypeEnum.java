package com.test.tyrelocation.common.emum;

import lombok.Getter;

/**
 * @Date: 2019/12/3 22:53
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Getter
public enum MessageTypeEnum {
    PRODUCT(1, "产品消息"),
    SAFETY(2, "安全消息"),
    SERVICE(3, "服务消息"),
    ACTIVITY(4, "活动消息"),
    FAULT(5, "故障消息")
    ;

    private int code;

    private String message;

    MessageTypeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
