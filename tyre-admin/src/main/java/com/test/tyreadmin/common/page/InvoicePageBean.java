package com.test.tyreadmin.common.page;

import lombok.Data;

/**
 * @Date: 2020/4/25 23:43
 * @Author: JackLei
 * @Description:
 * @Version:
 */
@Data
public class InvoicePageBean extends PageQueryBean{

    private int status;

    public InvoicePageBean(int page, int status){
        super.setCurrentPage(page);
        this.status = status;
    }
}
