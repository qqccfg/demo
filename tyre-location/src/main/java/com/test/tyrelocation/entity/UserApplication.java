package com.test.tyrelocation.entity;

import lombok.Data;
import java.io.Serializable;

@Data
public class UserApplication implements Serializable {
    private Long id;

    private Long userId;

    private Long applicationId;

    private static final long serialVersionUID = 1L;

    public UserApplication(Long userId, Long applicationId) {
        this.userId = userId;
        this.applicationId = applicationId;
    }

    public UserApplication() {
    }
}