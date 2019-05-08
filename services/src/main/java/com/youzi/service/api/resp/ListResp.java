package com.youzi.service.api.resp;


import java.util.List;

/**
 * Created by zzw on 2019/5/7.
 * 描述:
 */
public class ListResp<T> {

    private int total;
    private List<T> rows;

    public int getTotal() {
        return total;
    }

    public List<T> getRows() {
        return rows;
    }
}
