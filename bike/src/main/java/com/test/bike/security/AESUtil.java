package com.test.bike.security;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author JackLei
 * @Date: Created in 2018/4/25 19:44
 * @Description
 */
public class AESUtil {

    private static final String KEY_ALGORITHM="AES";
    private static final String KEY_ALGORITHM_MODE="AES/CBC/PKCS5Padding";

    /**
    *@Author JackLei
    *@Date: 2018/4/25 20:02
    *@Description: AES非对称加密
    */
    public static String encrypt(String data,String key) {
        try{
            SecretKeySpec spec=new SecretKeySpec(key.getBytes("UTF-8"),KEY_ALGORITHM);
            Cipher cipher=Cipher.getInstance(KEY_ALGORITHM_MODE);
            cipher.init(Cipher.ENCRYPT_MODE,spec,new IvParameterSpec(new byte[cipher.getBlockSize()]));
            byte[] bs=cipher.doFinal(data.getBytes("UTF-8"));
            return Base64Util.encode(bs);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
    *@Author JackLei
    *@Date: 2018/4/25 20:10
    *@Description: AES非对称解密
    */
    public static String decrypt(String data,String key){
        try {
            SecretKeySpec spec=new SecretKeySpec(key.getBytes("UTF-8"),KEY_ALGORITHM);
            Cipher cipher=Cipher.getInstance(KEY_ALGORITHM_MODE);
            cipher.init(Cipher.DECRYPT_MODE,spec,new IvParameterSpec(new byte[cipher.getBlockSize()]));
            byte[] originBytes=Base64Util.decode(data);
            byte[] result=cipher.doFinal(originBytes);
            return new String(result,"UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
       /* String key="1234567891012131";
        String data="{'mobile':'15277351384','platform':'android','code':'6666'}";
        //本地加密
        String ept=encrypt(data,key);
        System.out.println(ept);
        byte[] eptKey=RSAUtil.encryptByPublic(key.getBytes("UTF-8"),RSAUtil.PUBLIC_KEY);
        String base = Base64Util.encode(eptKey);
        System.out.println(base);*/
        //网络传输
        //服务器解密
//        byte[] dptkey=RSAUtil.decryptByPrivateKey(Base64Util.decode(base));
//        System.out.println(new String(dptkey,"UTF-8"));
//       String dpt=decrypt(ept,new String(dptkey,"UTF-8"));
//       System.out.println(dpt);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = sdf.format(new Date());
        System.out.println(timestamp);

    }
}
