package com.test.tyrelocation.service;

import com.test.tyrelocation.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @Date: 2019/9/22 16:04
 * @Author: JackLei
 * @Description:
 * @Version:
 */
public interface OperationService {
    String test (Image img) throws IOException;
}
