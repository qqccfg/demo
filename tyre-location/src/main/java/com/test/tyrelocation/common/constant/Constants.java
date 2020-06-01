package com.test.tyrelocation.common.constant;

/**
 * @Date: 2019/11/19 10:19
 * @Author: JackLei
 * @Description:
 * @Version:
 */
public class Constants {

    public static final String PAY_ORDER_HEAD="user_pay_order_";

    public static final String PAY_AMOUNT_HEAD="user_pay_amount_";

    public static final int ZERO = 0;

    public static final String VERIFY_CODE_REG = "zc";

    public static final String VERIFY_CODE_RET = "zh";


    public static final int PAID = 1;   //已支付

    public static final int UNPAID = 2;     //待支付

    public static final int  NON_PAYMENT = 3;     //交易失败

    public static final int VOUCHER_OVERDUE = 3;     //优惠券过期

    public static final String IMAGE_DIR = "E:\\logs\\image.png";

    public static final Long TYRE_API_ID = 1L;

    public static final Long OCR_TEXT_API_ID = 2L;

    public static final Long OCR_PLATE_LICENSE_API_ID = 3L;

    public static final String BAIDU_CLIENT_ID = "3oa6T5xfYa8qFMWlY2PYVRPu";

    public static final String BAIDU_CLIENT_SECRET = "F4EGo4ZE0LimqAGnLeGH03M7dQsD1ObX";

    public static final String BAIDU_AUTH_HOST = "https://aip.baidubce.com/oauth/2.0/token";

    public static final String BAIDU_TYRE_DETECTION_URL = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/detection/tyres";

    public static final int MSG_NOTICE = 1;

    public static final int MSG_UPGRADE = 2;

    public static final int MSG_NEW_PRODUCT = 3;

    public static final int MSG_OTHER = 4;


    public static final Long NOT_VOUCHER = 0L;

    public static final int VOUCHER_USED = 2;
    public static final Integer PAY_ALIPAY = 1;


    public static final Byte UNABLE = 2;
}
