package com.test.tyrelocation.common.tool;

import java.io.File;

/**
 * @Date: 2019/9/24 18:17
 * @Author: JackLei
 * @Description:
 * @Version:
 */
public class FileUtils {
    public static void delete(String dir){
        File file = new File("dir");
        if (file.exists()){
            file.delete();
        }
    }
}
