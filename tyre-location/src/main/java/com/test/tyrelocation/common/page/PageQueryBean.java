package com.test.tyrelocation.common.page;

import java.util.List;

/**
 * @Date: 2019/12/3 19:09
 * @Author: JackLei
 * @Description: 分页查询
 * @Version:
 */
public class PageQueryBean {

    private static final int DEFAULT_PAGE_SIZE = 10;

    /** 最多显示5个标签*/
    /**开始标签 */
    private Integer startLabel;

    /**结束标签 */
    private Integer endLabel;

    /** 当前页*/
    private Integer currentPage;

    /** 每页显示的条数*/
    private Integer pageSize;

    /** 所有记录数*/
    private Integer totalRows;

    /** sql查询起始行*/
    private Integer startRow;

    /** 总页数*/
    private Integer totalPage;

    /** 查询所得数据集*/
    private List<?> items;

    public  Integer getCurrentPage() {
        return currentPage;
    }

    public  void setCurrentPage(Integer currentPage) {
        int finalCurrentPage = currentPage<1 ? 1 : currentPage;
        this.currentPage = finalCurrentPage;
        setStartRow((finalCurrentPage-1)*getPageSize());
    }


    public  void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public  Integer getPageSize() {
        return pageSize!=null ? pageSize : DEFAULT_PAGE_SIZE;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
        int pageNum = getPageSize();
        int repair = totalRows%pageNum==0 ? 0 : 1;
        int allPage = totalRows/pageNum + repair;
        setTotalPage(allPage);
    }

    public  Integer getTotalRows() {
        return totalRows;
    }

    public  Integer getStartRow() {
        return startRow;
    }

    private  void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public  Integer getTotalPage() {
        return totalPage!=null ? totalPage : 0;
    }

    private  void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
        if(totalPage<=5){
            setStartLabel(1);
            setEndLabel(totalPage);
        }else {
            if ((getCurrentPage()+2)>=totalPage){
                setStartLabel(totalPage-4);
                setEndLabel(totalPage);
            }else if (getCurrentPage()>3){
                setStartLabel(getCurrentPage()-2);
                setEndLabel(getCurrentPage()+2);
            }else {
                setStartLabel(1);
                setEndLabel(5);
            }
        }
    }

    public  List<?> getItems() {
        return items;
    }

    public  void setItems(List<?> items) {
        this.items = items;
    }

    public Integer getStartLabel() {
        return startLabel;
    }

    public void setStartLabel(Integer startLabel) {
        this.startLabel = startLabel;
    }

    public Integer getEndLabel() {
        return endLabel;
    }

    public void setEndLabel(Integer endLabel) {
        this.endLabel = endLabel;
    }
}
