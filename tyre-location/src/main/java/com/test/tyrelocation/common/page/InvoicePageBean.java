package com.test.tyrelocation.common.page;

/**
 * @Date: 2019/12/17 20:33
 * @Author: JackLei
 * @Description:
 * @Version:
 */
public class InvoicePageBean extends PageQueryBean{

    private int status;

    public InvoicePageBean() {
    }

    public InvoicePageBean(int status, int page) {
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
