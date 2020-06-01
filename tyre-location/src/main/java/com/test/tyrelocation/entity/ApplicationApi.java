package com.test.tyrelocation.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ApplicationApi implements Serializable {
    private Long id;

    private Long applicationId;

    private Long apiId;

    public ApplicationApi() {
    }

    public ApplicationApi(Long applicationId, Long apiId) {
        this.applicationId = applicationId;
        this.apiId = apiId;
    }

    private static final long serialVersionUID = 1L;


}