package com.test.bike.common.constants;

/**
 * @Author JackLei
 * @Date: Created in 2018/4/26 19:38
 * @Description 存放常量的类
 */
public class Constants {
    /**自定义的状态码 start**/
    public static final int RESP_STATUS_OK=200;

    public static final int RESP_STATUS_NOAUTH=401;

    public static final int RESP_STATUS_INTERNAL_ERROR=500;

    public static final int RESP_STATUS_BAD_REQUEST=400;
    /**自定义的状态码 end**/

    /**请求头token**/
    public static final String REQUEST_TOKEN_KEY ="user-token" ;

    /**客户端版本**/
    public static final String REQUEST_VERSION_KEY = "version";

    /**秒滴start**/
    public static final String ACCOUNT_SID="1cade30492144401b959bc58b86fb5de";

    public static final String AUTH_TOKEN="7d6922b968ba4008a3de44c9634ab5a0";

    public static final String MDSMS_REST_URL = "https://api.miaodiyun.com/20150822";

    public static final String MDSMS_VERCODE_TPLID = "230655959";

    /**秒滴end**/

    /**验证码start**/
    public static final String LODIN_VERCODE_TYPE="reg";

    public static final int EXP_TIME=60;
    /**验证码end**/

    /**七牛配置信息start**/
    public static final String QINIU_ACCESS_KEY="nH0cbY7_Wxt8kpyud84BIEXhHl6QN62RkkSRjByK";

    public static final String QINIU_SECRET_KEY="FK_5GPzR1sf8BqQqBdpXuW-e6tuNdE_dzgxO0i3n";

    public static final String QINIU_BUCKET_NAME="headphoto";

    public static final String QINIU_BUCKET_URL="p834ryqla.bkt.clouddn.com";
    /**七牛配置信息end**/
    /**附近单车查询配置start**/
    public static final String MONGODB_COLLECTION="bike-position";

    public static final long MONGODB_MAX=100;

    public static final int MONGODB_LIMT=20;

    /**附近单车查询配置end**/

    /**百度云推送start**/
    public static final String BAIDUYUN_APIKEY="zvZbXqysxW4OEsA3b6sK0VXQ";

    public static final String BAIDUYUN_SECRETKEY="tqBM16A8DrjW0DMSGa3Oij1GDYGwpQxp";

    public static final String BAIDUYUN_URL="api.tuisong.baidu.com";
    /**百度云推送end**/
}
