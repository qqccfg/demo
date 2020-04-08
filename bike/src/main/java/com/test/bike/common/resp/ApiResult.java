package com.test.bike.common.resp;

import com.test.bike.common.constants.Constants;
import lombok.Data;

/**
 * @Author JackLei
 * @Date: Created in 2018/4/26 19:34
 * @Description 返回数据的类
 */
@Data
public class ApiResult<T> {
    private int code= Constants.RESP_STATUS_OK;

    private String message;

    private T date;
}
