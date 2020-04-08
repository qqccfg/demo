package com.test.bike.bike.entity;

import lombok.Data;

/**
 * @Author JackLei
 * @Date: Created in 2018/5/3 17:50
 * @Description 坐标
 */
@Data
public class Point {


    public Point() {
    }

    public Point(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    private double longitude;

    private double latitude;
}
