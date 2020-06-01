package com.test.tyrelocation.common.emum;

import lombok.Getter;

/**
 * @Date: 2019/11/15 13:43
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Getter
public enum ApiTypeEnum {
    OBJECT_DETECTION(1, "物体检测"),
    ACTION_DETECTION(2, "行为检测"),
    IMAGE_CLASSIFICATION(3, "图像分类")
    ;
    private int code;

    private String message;

    ApiTypeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
