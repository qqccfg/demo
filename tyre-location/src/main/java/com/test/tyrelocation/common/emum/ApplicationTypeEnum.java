package com.test.tyrelocation.common.emum;

import lombok.Getter;

/**
 * @Date: 2019/10/29 12:48
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Getter
public enum ApplicationTypeEnum {
    GAME_ENTERTAINMENT(1, "游戏娱乐"),
    TOOL_APPLICATION(2, "工具应用"),
    LEARNING_OFFICE(3, "学习工具"),
    INTELLIGENCE_HARDWARE(4, "智能硬件"),
    CHAT_COMMUNITY(5, "聊天社区"),
    OTHER(6 ,"其他")
    ;
    private int code;

    private String message;

    ApplicationTypeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
