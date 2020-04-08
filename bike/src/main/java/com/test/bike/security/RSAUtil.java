package com.test.bike.security;

import com.test.bike.sms.MiaoDiSendImpl;
import com.test.bike.sms.SmsSend;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.Cipher;
import java.io.InputStream;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


/**
 * @Author JackLei
 * @Date: Created in 2018/4/25 20:46
 * @Description
 */
public class RSAUtil {


    /**
     * 私钥字符串
     */
    private static String PRIVATE_KEY ="";
    /**
     * 公钥字符串
     */
    public static String PUBLIC_KEY ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDFF/HR0JE4dzN2vXRrdvLS RQqNQq8K2rySMbIsr/Hrxto8amZ7rJUHU7DVQjTcttpsZ59zn/TsiaFdObbA G+pH6JSXIWu/dZt5BTVdECU0sfxTOGJwVEvgvyVVK+1rsUFHfrVFLLWIMhi+ hyOzOav9qHEdYTfAwrlqcZ9D60M/MwIDAQAB";


    private static final String KEY_ALGORITHM = "RSA";
/**
*@Author JackLei
*@Date: 2018/4/25 20:59
*@Description: 读取秘钥
*/
    public static void convert() throws Exception {
        byte[] data = null;

        try {
            InputStream is = RSAUtil.class.getResourceAsStream("/enc_pri");
            int length = is.available();
            data = new byte[length];
            is.read(data);
        } catch (Exception e) {
        }

        String dataStr = new String(data);
        try {
            PRIVATE_KEY = dataStr;
        } catch (Exception e) {
        }

        if (PRIVATE_KEY == null) {
            throw new Exception("Fail to retrieve key");
        }
    }
/**
*@Author JackLei
*@Date: 2018/4/25 20:56
*@Description: RSA加密对称
*/
  public static byte[] encryptByPublic(byte[] data,String key) throws Exception {
      byte[] keyBytes=Base64Util.decode(key);
      X509EncodedKeySpec pkcs8KeySpec = new X509EncodedKeySpec(keyBytes);
      KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);
      Key publicKey = keyFactory.generatePublic(pkcs8KeySpec);
      Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
      cipher.init(Cipher.ENCRYPT_MODE, publicKey);
      return cipher.doFinal(data);
  }
    public static byte[] decryptByPrivateKey(byte[] data) throws Exception {
        convert();
        byte[] keyBytes = Base64Util.decode(PRIVATE_KEY);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
//        KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance(KEY_ALGORITHM);
//        keyPairGenerator.initialize(1024);
//        KeyPair keyPair=keyPairGenerator.generateKeyPair();
//        PrivateKey privateKey=keyPair.getPrivate();
//        PublicKey publicKey=keyPair.getPublic();
//        System.out.println(Base64Util.encode(privateKey.getEncoded()));
//        System.out.println();
//        System.out.println();
//        System.out.println(Base64Util.encode(publicKey.getEncoded()));
        MiaoDiSendImpl miaoDiSend=new MiaoDiSendImpl();
        miaoDiSend.sendSms("15277351384","230655959","6666");

    }
}
