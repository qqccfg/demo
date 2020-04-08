package com.test.bike.common.utils;

import lombok.extern.slf4j.Slf4j;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author JackLei
 * @Date: Created in 2018/5/1 0:25
 * @Description
 */
@Slf4j
public class HttpUtil {
    private static final String ENDCODE="UTF-8";

    public static String post(String url, Map<String,String> paramsMap){
        String responseText="";
        CloseableHttpClient client= HttpClients.createDefault();
        CloseableHttpResponse response=null;
        try {
            HttpPost method=new HttpPost(url);
            if (paramsMap!=null){
                List<NameValuePair> pairList=new ArrayList<>();
                for (Map.Entry<String,String> paramlist:paramsMap.entrySet()){
                    NameValuePair pair=new BasicNameValuePair(paramlist.getKey(),paramlist.getValue());
                    pairList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(pairList,ENDCODE));
            }
            response=client.execute(method);
            HttpEntity entity=response.getEntity();
            if (entity!=null){
                responseText= EntityUtils.toString(entity);
            }


        }catch (Exception e){
            log.error("http request falied",e);
        }finally {
            try {
                response.close();
                client.close();
            }catch (Exception e){}
        }
        return responseText;

    }
}
