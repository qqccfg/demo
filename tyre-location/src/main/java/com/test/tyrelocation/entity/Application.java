package com.test.tyrelocation.entity;


import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Application implements Serializable {

    private Long id;

    @NotNull(message = "应用名称不能为空")
    private String applicationName;

    @Min(1)
    @Max(6)
    private int applicationType;

    @Null(message = "字段apiKey非法")
    private String apiKey;

    @Null(message = "字段secretKey非法")
    private String secretKey;

    @Size(max = 500, message = "描述最多为500字")
    private String description;

    @Null(message = "字段gmtCreate非法")
    private Date gmtCreate;

    @Null(message = "字段gmtCreate非法")
    private Date gmtModified;

    @NotNull(message = "apis选择不能为空")
    private List<Api> apis;

    private static final long serialVersionUID = 1L;

}