package com.test.tyrelocation.common.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Date: 2020/2/19 18:39
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Configuration
public class AliPayConfig {

    //↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016101800715964";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCQdcWgJ8Teaj00gru0WKO/y0OUm7HxNYcpWNAcp/k1Y+dCIZVs0oMTAqx228VvYqQh/FJ2oghQ5FVpZYOwY4AmwgQ/RE5AQuPhFa+EmNix4m6QfAhwWd6VLu/VmJpZtZ7GkveDitPdIlD5RGh0LMYNYCQ7tHJMWlXAgo0gUnBULJaunMombDxapr18VR51Ze73ghfRvh7spkoqWfjODm6kUM6HJpwg4MSKJeHVZ/WYeP6DE7OPxEFUQrhkEb7uzwqg6QnWtsOgYqP+qK8X9tLMie2Xss6oneaTIYOY+Qu2BhJWpqsY09jL2naDBQSFn9Op4eci/onmZQReau4huWTpAgMBAAECggEAQApFsusOZj8YpPezuI53ASZd7jpbVwAQzC6YC6wXWSjRs0pqEJT+EGZmCEcDLo6/DmXqNLc+EoY2qwOaro5dmFD4Swe/3NMKbJUNey27GpizxQ+HOi9M8judjMYa3twZ6BPs7/6ST3zQAsDlwWK75eASD/m9lcWSMEZvw71oGJgEcc22kweydEKT7mJDJzvDLec82V+s/oiTnNfOjDIv+9f6DrL57laHaKBVoE7M5Nuo4ahPJ1NolDACOifaqHsrKyRyAp7GO/wE2R3gSA8VSUd3ZnVVjRbHasRjc8n7+1HT2+rfwDA8rD4y39guMYozXhQs50qpMXfQSoWt/hSKgQKBgQDTGCgdx01dZqIDsrPQapk2A/iGNOIDn32H5Ka40wlmkUAGuXImkCG3UOcwtXUeGjGBSM9pkmxdXBzQDZmy+UjBZ3a45a4HfFLCHj1OSSPvWB8LIBW0FbyphesZTHrTHqrUEnhbRB4JzNJsRLbwiRSlA/MMo66puDYwAuXlzl0f+QKBgQCvMNKeNrny4FeWVJ7nRP3RADRhroNstXbqWhzaIy0IueuNhvmP6LLOUSuan5jHH+LCM3rwgyHFbqqIyx1Xy+sTKch7eVdBXSG8YfEdhiS5/BcQVynQdOZ30cNhH94r2yCQ5NNNC8Fw4qaXwWJV2JJdSO6N15SZ2tXGJEEZqzKIcQKBgEfPEeZ/Y+v/++EyK0Uc3h2n4CUXXENLQBK4T5bUA4CmrJcdAp3nrfCETwEc2UhyNKD8OcJ6Eu73ZL3Jwp1Fe+B+574iM79MJYLvcn9tFhJXccgBFCD8u/8XFVC32Zhz+DzIQ7UYSPege3xlK65Lk4BHBakpDUOi7JwMgFRajQdRAoGAOBihCEorxopxYVCdRR6BS4AORcz0hkWg2y+Zl3u1+tpVg47ZsGvpfUjednduGyyplleXoRXMJYTnFEA9mBRSpGM3r9nNx79Dgye1ZifC8M64r+HJINedyHYhYqMqp85Voi+zPTNrcHMStvsUOrrLOxWUVVTXX2CEHRsUwVxhJPECgYATQkSQrR18JIa5lQZCB9LdCxP4nhFpPf1odrzEOXcDfKbQDXpsJZk2SDTISClJ1roLKOt73cVSBLta+dDaD+cJSt7zp6ATxC2hoIAQqBbNR22WruDKt+iuRdPUaGtjvl4yXKAhtszIEH70dqbz+hliFK+1VO3O2AFVS8kkLyJRZA==";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnZ+Zc8btXajhoJ+Uo1/XJkVBhJxhiu55CB4w9sLQmGHo7QH7LEvRlTatnylPyRL062ZxENyc0j8egf68uGMtrTMqIwb4iNjTaIfktqIhp+An8M000e3/2WGMnWLbJ86Zfm+jNHt0+tFFbbQLAnvOfmdnWz+Uk3iTw7IpZO9UGDDVoKgyymC/G0eTVicSsaIyLesb9dRV2K/hUaVsxFzwFIKuRLqcp5Z1U+c0cGizsS6cHL4+uxCewTR/J23gf9FCFDli87ho3C1mr1Rhb39uJRnDDBUDE1v65B6z6D7pSuFhrusqbTac4FfJkaQrmPhzJDy86F/PqMPhyBI2SR0T5QIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://tyre.natapp1.cc/pay/notify/url";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://tyre.natapp1.cc/pay/result/url";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    @Bean
    public AlipayClient alipayClient(){
        return new DefaultAlipayClient(gatewayUrl,  app_id,  merchant_private_key
                ,"json", charset, alipay_public_key, sign_type);
    }
}
