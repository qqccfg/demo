package com.test.bike.common.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.test.bike.common.constants.Constants;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author JackLei
 * @Date: Created in 2018/5/1 19:33
 * @Description
 */
public class QiniuFileUploadUtils {
    public static String UploadImg(MultipartFile file) throws IOException {
        Configuration cfg = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(cfg);
        String key = null;
        Auth auth = Auth.create(Constants.QINIU_ACCESS_KEY, Constants.QINIU_SECRET_KEY);
        String upToken = auth.uploadToken(Constants.QINIU_BUCKET_NAME);
        Response response = uploadManager.put(file.getBytes(), key, upToken);
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        return putRet.key;
    }
}
