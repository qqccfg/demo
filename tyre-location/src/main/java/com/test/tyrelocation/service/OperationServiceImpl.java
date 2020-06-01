package com.test.tyrelocation.service;

import com.test.tyrelocation.entity.Image;
import com.test.tyrelocation.opencv.ImgDetecation;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import java.io.*;
import java.util.UUID;

/**
 * @Date: 2019/9/22 16:05
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Service
public class OperationServiceImpl implements OperationService {



    @Override
    public String test(Image img)  {
        String date = img.getData().substring(img.getData().indexOf(",")+1);
        byte[] arrayDate = Base64Utils.decodeFromString(date);
        String retSuffix = UUID.randomUUID().toString()+"."+img.getSuffix();
        String dir = img.getRepository()+"\\"+retSuffix;
        FileOutputStream outputStream =null;
        FileInputStream inputStream = null;
        try {
            outputStream = new FileOutputStream(dir);
            outputStream.write(arrayDate);
            ImgDetecation.doDetection(dir,img.getSuffix());
            inputStream = new FileInputStream(dir+"."+img.getSuffix());
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            String encode = Base64Utils.encodeToString(buffer);
            return  "data:image/"+img.getSuffix()+";base64,"+encode;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";

    }
}
