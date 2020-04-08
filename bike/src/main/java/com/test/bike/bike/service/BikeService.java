package com.test.bike.bike.service;

import com.test.bike.bike.entity.BikeLocation;
import com.test.bike.common.exception.TestBikeException;
import com.test.bike.user.entity.UserElement;

/**
 * @Author JackLei
 * @Date: Created in 2018/5/2 22:47
 * @Description
 */
public interface BikeService {
    void generateBike() throws TestBikeException;

    void unlockBike(UserElement currentUser, Long number) throws TestBikeException;

    void lockBike(BikeLocation bikeLocation)throws TestBikeException;

    void uploadPoint(BikeLocation bikeLocation)throws TestBikeException;

    Byte userQueryBikeStatus(Long number);
}
