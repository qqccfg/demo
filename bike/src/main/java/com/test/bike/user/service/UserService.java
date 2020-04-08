package com.test.bike.user.service;

import com.test.bike.common.exception.TestBikeException;
import com.test.bike.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author JackLei
 * @Date: Created in 2018/4/24 22:02
 * @Description
 */
public interface UserService {
    String loging(String data, String key) throws TestBikeException;

    void modifNickname(User user) throws TestBikeException;

    void sendVercode(String mobile, String ipFormRequest) throws TestBikeException;

    String modifHeadportrait(long userId, MultipartFile file) throws TestBikeException;
}
