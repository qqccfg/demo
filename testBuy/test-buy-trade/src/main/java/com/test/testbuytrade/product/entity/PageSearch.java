package com.test.testbuytrade.product.entity;

import lombok.Data;

/**
 * @author JackLei
 * @Title: PageSearch
 * @ProjectName testBuy
 * @Description:
 * @date 2018/6/1921:18
 **/
@Data
public class PageSearch {
    private String searchName;

    private int pageSize;

    private int pageNumber;
}
