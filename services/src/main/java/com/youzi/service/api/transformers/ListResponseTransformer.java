package com.youzi.service.api.transformers;


import com.youzi.framework.common.error.CodeException;
import com.youzi.framework.common.error.EmptyDataException;
import com.youzi.service.api.resp.ListResp;
import com.youzi.service.api.resp.Resp;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * Created by LuoHaifeng on 2018/4/11 0011.
 * Email:496349136@qq.com
 */
public class ListResponseTransformer<T> implements ObservableTransformer<ListResp<T>, List<T>> {
    private ListResponseTransformer() {
    }


    public static <T> ListResponseTransformer<T> create() {
        return new ListResponseTransformer<T>() {
        };
    }

    @Override
    public ObservableSource<List<T>> apply(Observable<ListResp<T>> tResp) {
        return tResp.map(new Function<ListResp<T>, List<T>>() {
            @Override
            public List<T> apply(ListResp<T> tListResp) throws Exception {
                return tListResp.getRows();
            }
        });
    }
}
