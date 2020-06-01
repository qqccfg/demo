package com.test.tyrelocation.common.tool;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * @Date: 2020/3/16 10:42
 * @Author: JackLei
 * @Description:
 * @Version:
 */
public class BaiduSdk {
    private static final String APP_ID = "14634718";

    private static final String API_KEY = "vIkuyYknAXDUkvq3N7kHMwyR";

    private static final String SECRET_KEY = "ikLg746VUGGpVdyEeWkULVDF1xUiv3fu";

    private static AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

    public static void main(String[] args) {

    }
    public static String textOcr(byte[] data){
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(6000);
        String imagePath = "C:\\Users\\Shinelon\\Desktop\\test.png";
        JSONObject jsonObject = client.basicAccurateGeneral(data, new HashMap<>());
        JSONArray words = jsonObject.getJSONArray("words_result");
        StringBuilder result = new StringBuilder();
        for (int i=0; i<words.length(); i++){
            String word = words.getJSONObject(i).getString("words");
            result.append(word+"\n");
        }
        return result.toString();
    }

    public static String plateLicense(byte[] data){
        JSONObject jsonObject = client.plateLicense(data, new HashMap<>());
        return jsonObject.getJSONObject("words_result").getString("number");
    }
}
