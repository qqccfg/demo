package com.test.testbuystock.common.constants;

import lombok.Data;

/**
 * @author JackLei
 * @Title: Constants
 * @ProjectName testBuy
 * @Description:
 * @date 2018/6/2117:07
 **/
@Data
public class Constants {
    /**自定义状态码 start**/
    public static final int RESP_STATUS_OK = 200;

    public static final int RESP_STATUS_NOAUTH = 401;

    public static final int RESP_STATUS_INTERNAL_ERROR = 500;

    public static final int RESP_STATUS_BADREQUEST = 400;

    /**自定义状态码 end**/

    public static final String CACHE_PRODUCT_STOCK="{product:stock}";
    public static final String CACHE_PRODUCT_LOCK_STOCK ="{product:stock}:lock";
    public static final String ORDER_RETRY_LOCK = "trade:retry:lock";
}
