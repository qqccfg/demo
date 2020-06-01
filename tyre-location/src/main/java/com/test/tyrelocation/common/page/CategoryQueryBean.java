package com.test.tyrelocation.common.page;

/**
 * @Date: 2019/12/3 20:39
 * @Author: JackLei
 * @Description:
 * @Version:
 */
public class CategoryQueryBean extends PageQueryBean{

    private int category;

    private int status;

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
