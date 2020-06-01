package com.test.tyreadmin.entity;

import java.io.Serializable;
import java.util.Date;

public class Address implements Serializable {
    private Long id;

    private Long userId;

    private String relationName;

    private String mainZone;

    private String detailZone;

    private String mailCode;

    private String mobile;

    private Integer isDefault;

    private Date gmtCreate;

    private Date gmtModified;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName == null ? null : relationName.trim();
    }

    public String getMainZone() {
        return mainZone;
    }

    public void setMainZone(String mainZone) {
        this.mainZone = mainZone == null ? null : mainZone.trim();
    }

    public String getDetailZone() {
        return detailZone;
    }

    public void setDetailZone(String detailZone) {
        this.detailZone = detailZone == null ? null : detailZone.trim();
    }

    public String getMailCode() {
        return mailCode;
    }

    public void setMailCode(String mailCode) {
        this.mailCode = mailCode == null ? null : mailCode.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
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