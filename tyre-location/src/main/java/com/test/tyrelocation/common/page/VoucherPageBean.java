package com.test.tyrelocation.common.page;

import javax.validation.constraints.Size;

/**
 * @Date: 2019/12/11 18:53
 * @Author: JackLei
 * @Description:
 * @Version:
 */
public class VoucherPageBean extends PageQueryBean{
    //0 全部  1待使用    2已使用    3过期
    private int status;

    public VoucherPageBean() {
    }

    public VoucherPageBean(int status, int page) {
        this.status = status;
        super.setCurrentPage(page);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
