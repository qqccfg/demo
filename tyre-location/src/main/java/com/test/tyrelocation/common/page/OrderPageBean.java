package com.test.tyrelocation.common.page;

import lombok.Data;

/**
 * @Date: 2019/12/19 21:08
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Data
public class OrderPageBean extends PageQueryBean{

    private int status;

    public OrderPageBean() {
    }

    public OrderPageBean(int status, int page) {
        this.status = status;
        super.setCurrentPage(page);
    }
}
