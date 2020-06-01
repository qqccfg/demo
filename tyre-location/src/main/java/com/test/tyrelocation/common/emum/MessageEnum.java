package com.test.tyrelocation.common.emum;

import lombok.Getter;

/**
 * @Date: 2019/11/28 17:17
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Getter
public enum MessageEnum {
    SYS_NOTICE(1, "notice"),
    SYS_UPGRADE(2, "upgrade"),
    SYS_NEW_PRODUCT(3, "newProduct"),
    SYS_MSG_OTHER(4, "other"),

    MSG_STATUS_NORMAL(1, "正常"),
    MSG_STATUS_READ(2, "已读"),
    MSG_STATUS_DELETE(3, "删除")
    ;

    private int code;

    private String message;

    MessageEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
