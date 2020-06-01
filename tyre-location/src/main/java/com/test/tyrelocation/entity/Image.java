package com.test.tyrelocation.entity;

import java.io.Serializable;

/**
 * @Date: 2019/9/23 13:03
 * @Author: JackLei
 * @Description:
 * @Version:
 */
public class Image implements Serializable {

    public Image(String data, int width, int height, String suffix) {
        this.data = data;
        this.width = width;
        this.height = height;
        this.suffix = suffix;
    }

    public Image() {
    }

    private String data;

    private Integer width;

    private Integer height;

    private String suffix;

    private String repository;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }
}
