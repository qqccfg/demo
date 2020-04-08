package com.test.testbuyuser.common.constants;

/**
 * @Author JackLei
 * @Date: Created in 2018/6/11 21:33
 * @Description
 */
public class Constants {
    /**自定义状态码 start**/
    public static final int RESP_STATUS_OK = 200;

    public static final int RESP_STATUS_NOAUTH = 401;

    public static final int RESP_STATUS_INTERNAL_ERROR = 500;

    public static final int RESP_STATUS_BADREQUEST = 400;
    /**自定义状态码 end**/

    /**用户token**/
    public static final String REQUEST_TOKEN_HEADER="x-auth-token";

    /**用户session**/
    public static final String REQUEST_USER_SESSION="current-user";

    /**用户注册分布式锁路径***/
    public static final String USER_REGISTER_DISTRIBUTE_LOCK_PATH = "/user_reg";
}
