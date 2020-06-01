package com.test.tyreadmin.entity;

import java.io.Serializable;
import java.util.Date;

public class Api implements Serializable {
    private Long id;

    private String apiName;

    private Integer apiType;

    private String apiUrl;

    private Integer apiLimit;

    private Byte apiStatus;

    private Date gmtCreate;

    private Date gmtModified;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName == null ? null : apiName.trim();
    }

    public Integer getApiType() {
        return apiType;
    }

    public void setApiType(Integer apiType) {
        this.apiType = apiType;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl == null ? null : apiUrl.trim();
    }

    public Integer getApiLimit() {
        return apiLimit;
    }

    public void setApiLimit(Integer apiLimit) {
        this.apiLimit = apiLimit;
    }

    public Byte getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(Byte apiStatus) {
        this.apiStatus = apiStatus;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}