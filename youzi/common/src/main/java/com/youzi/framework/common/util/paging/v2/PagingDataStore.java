package com.youzi.framework.common.util.paging.v2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuoHaifeng on 2018/5/21 0021.
 * Email:496349136@qq.com
 */
public class PagingDataStore<T> {
    private Paging<T> paging;
    private List<T> dataSet = new ArrayList<>();

    public PagingDataStore(Paging<T> paging) {
        this.paging = paging;
    }

    public void onPagingResult( T data) {
        dataSet.add(data);
    }

    public void clear(){
        dataSet.clear();
    }

    public List<T> getDataSet() {
        return dataSet;
    }
}
