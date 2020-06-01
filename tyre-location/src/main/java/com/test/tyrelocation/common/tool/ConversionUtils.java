package com.test.tyrelocation.common.tool;

import com.test.tyrelocation.common.emum.ApiTypeEnum;
import com.test.tyrelocation.common.emum.MessageEnum;
import com.test.tyrelocation.common.emum.MessageTypeEnum;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Date: 2019/11/15 13:53
 * @Author: JackLei
 * @Description:
 * @Version:
 */
public class ConversionUtils {
    public static void main(String[] args) {
        System.out.println(apiTypeToString(3));
    }
    public static String apiTypeToString(Integer apiType) {
        List<ApiTypeEnum> apiTypeEnums = Arrays.stream(ApiTypeEnum.values()).filter(e ->{
            return e.getCode()==apiType;
        }).collect(Collectors.toList());
        return apiTypeEnums.get(0).getMessage();
    }

    public static String msgTypeToString(Integer msgType){
        List<MessageTypeEnum> messageTypeEnums = Arrays.stream(MessageTypeEnum.values()).filter(e->{
            return e.getCode() == msgType;
        }).collect(Collectors.toList());
        return messageTypeEnums.get(0).getMessage();
    }
}
