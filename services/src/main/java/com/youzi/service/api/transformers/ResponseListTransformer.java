package com.youzi.service.api.transformers;


import com.youzi.framework.common.error.CodeException;
import com.youzi.service.api.resp.Resp;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * Created by LuoHaifeng on 2018/4/11 0011.
 * Email:496349136@qq.com
 */
public class ResponseListTransformer<T extends List> implements ObservableTransformer<Resp<T>, T> {

    @Override
    public ObservableSource<T> apply(Observable<Resp<T>> upstream) {
        return upstream.map(new Function<Resp<T>, T>() {
            @Override
            public T apply(Resp<T> tResp) throws Exception {
                if (tResp.isSuccess() || tResp.getCode() == 400) {//成功
                    Object obj = new ArrayList<>();
                    //noinspection unchecked
                    return tResp.getData() == null ? (T) obj : tResp.getData();
                }
                throw new CodeException(tResp.getCode() + "", tResp.getMessage());
            }
        });
    }

    public static <T extends List> ResponseListTransformer<T> create() {
        return new ResponseListTransformer<>();
    }
}
