package com.youzi.framework.common.util.paging.v2;

/**
 * Created by LuoHaifeng on 2018/5/21 0021.
 * Email:496349136@qq.com
 */
public class PagingResult<T> {
    private int pageIndex;
    private int pageSize;
    private T result;
    private boolean isValidateData;

    public int getPageIndex() {
        return pageIndex;
    }

    public PagingResult<T> setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public PagingResult<T> setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public T getResult() {
        return result;
    }

    public PagingResult<T> setResult(T result) {
        this.result = result;
        return this;
    }

    public boolean isValidateData() {
        return isValidateData;
    }

    public PagingResult<T> setValidateData(boolean validateData) {
        isValidateData = validateData;
        return this;
    }
}
