package com.test.tyrelocation.common.tool;


import com.test.tyrelocation.common.Exception.TyreException;
import com.test.tyrelocation.common.emum.ExceptionEnum;
import org.springframework.util.DigestUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Date: 2019/10/29 13:50
 * @Author: JackLei
 * @Description:
 * @Version:
 */
public class Generator {

    private static final int[] PRICE_TABLE = {50, 248,  470, 860, 1850,  3250, 12000, 20000};

    private static final int[] NUMBER_TABLE = {10000, 50000,  100000, 200000, 500000,  1000000, 5000000, 10000000};

    public static String getApiKey(String encodeRules, Long id){
        return AESUtil.AESEncode(encodeRules, String.valueOf(id));
    }

    public static String getSecretKey(){
        return DigestUtils.md5DigestAsHex(UUID.randomUUID().toString().getBytes());
    }

    public static Long getId(){
        return System.currentTimeMillis();
    }

    /**
     * 格式为 yyyy-mm
     * @param num
     * @return
     */
    public static List<String> getMonth(int num){
        List<String> result = new LinkedList<>();
        Calendar currentCalendar = Calendar.getInstance();
        int year = currentCalendar.get(Calendar.YEAR);
        int month = currentCalendar.get(Calendar.MONTH)+1;
        for (int i=0; i<num; i++){
            month = month-1;
            if (month == 0){
                month = 12;
                year = year-1;
            }
            if (month<10){
                result.add(year+"-"+"0"+month);
            }else {
                result.add(year+"-"+month);
            }

        }
        return reverse(result);
    }
    /*
        格式为 yy-mm
     */
    public static List<String> getMonth2(int num){
        List<String> months = getMonth(num);
        List<String> result = months.stream().map(s-> s.substring(2))
                .collect(Collectors.toList());
        return result;
    }
    private static List<String> reverse(List<String> list){
        List<String> result = new LinkedList<>();
        for (int i = 0 ; i<list.size(); i++){
            result.add(list.get(list.size()-1-i));
        }
        return result;
    }

    /**
     *
     * @return 今天的时间格式为 yy-mm-dd
     */
    public static String getToday(){
        Calendar current = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("YY-MM-dd");
        return dateFormat.format(current.getTime());
    }
    public static String getCurrentMoth(){
        Calendar current = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("YY-MM");
        return dateFormat.format(current.getTime());
    }

    public static String getToken() {
        return UUID.randomUUID().toString();
    }

    public static int calculatePrice(String data){
        List<Integer> numbers = new ArrayList<>();
        String[] dataArray = data.split("H");
        try {
            for (String val : dataArray){
                numbers.add(Integer.parseInt(val));
            }
        }catch (Exception e){
            throw new TyreException(ExceptionEnum.PARAMETER_ILLEGALITY);
        }

        int sum = 0;
        for (int i=0; i<numbers.size(); i++){
            sum=sum+numbers.get(i)* PRICE_TABLE[i];
        }
        return sum;
    }

    public static int calculateNumber(String data){
        List<Integer> numbers = new ArrayList<>();
        String[] dataArray = data.split("H");
        try {
            for (String val : dataArray){
                numbers.add(Integer.parseInt(val));
            }
        }catch (Exception e){
            throw new TyreException(ExceptionEnum.PARAMETER_ILLEGALITY);
        }

        int sum = 0;
        for (int i=0; i<numbers.size(); i++){
            sum=sum+numbers.get(i)* NUMBER_TABLE[i];
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(getToken());
    }


}
