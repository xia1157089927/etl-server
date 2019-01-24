package com.xia.etl;

import java.util.List;
import java.util.Map;

public class Page {
    /**
     * 总条数
     */
    private long totalNumber;

    /**
     * 当前第几页
     */
    private long currentPage;

    /**
     * 总页数
     */
    private long totalPage;

    /**
     * 每页条数
     */
    private long pageNumber;

    /**
     * 结果集
     */
    private List<Map<String, Object>> result;

    public long getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(long totalNumber) {
        this.totalNumber = totalNumber;
    }

    public long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public List<Map<String, Object>> getResult() {
        return result;
    }

    public void setResult(List<Map<String, Object>> result) {
        this.result = result;
    }

    public Page(long currentPage, long pageNumber) {
        this.currentPage = currentPage;
        this.pageNumber = pageNumber;
    }

    @Override
    public String toString() {
        return "Page{" +
                "totalNumber=" + totalNumber +
                ", currentPage=" + currentPage +
                ", totalPage=" + totalPage +
                ", pageNumber=" + pageNumber +
                '}';
    }
}
