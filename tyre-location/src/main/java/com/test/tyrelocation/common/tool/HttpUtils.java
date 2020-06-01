package com.test.tyrelocation.common.tool;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Date: 2019/11/18 19:17
 * @Author: JackLei
 * @Description:
 * @Version:
 */
public class HttpUtils {
    private static final String[] HEADERS_TO_TRY = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR",
            "X-Real-IP"};
    private static final String ENCODING = "UTF-8";

    public static String getIpForRequest(HttpServletRequest request){
        for (String header : HEADERS_TO_TRY){
           String ip = request.getHeader(header);
           if (ip!=null && ip.length()!=0 && !("unknown").equals(ip)){
                return ip;
           }
        }
        return request.getRemoteAddr();
    }

    public static boolean isMobile(String mobile){
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (mobile.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(mobile);
            boolean isMatch = m.matches();
            return isMatch;
        }
    }

    public static boolean checkPassword(String val){
        String regex = "^(?=.*[a-zA-Z])(?=.*[1-9])(?=.*[\\W]).{8,14}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(val);
        boolean isMatch = m.matches();
        return isMatch;
    }

    public static String post(String url, Map<String, String> paramsMap) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            if (paramsMap != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList, ENCODING));
            }
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
            }
        } catch (Exception e) {

        } finally {
            try {
                response.close();
            } catch (Exception e) {

            }
        }
        return responseText;
    }

    public static String get(String url, Map<String, String> paramsMap) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            String getUrl = url+"?";
            if (paramsMap != null) {
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    getUrl += param.getKey() + "=" + URLEncoder.encode(param.getValue(), ENCODING)+"&";
                }
            }
            HttpGet method = new HttpGet(getUrl);
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
        } finally {
            try {
                response.close();
            } catch (Exception e) {
            }
        }
        return responseText;
    }

    public static void main(String[] args) {
        System.out.println(Calendar.getInstance().getTime().getTime());
        System.out.println(Calendar.getInstance());
        System.out.println(Calendar.getInstance().get(Calendar.MONTH));
        SimpleDateFormat format = new SimpleDateFormat("yy-mm");
        System.out.println(format.format(new Date()));

    }


}
