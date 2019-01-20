package com.youzi.service.api.transformers;


import com.youzi.framework.common.error.CodeException;
import com.youzi.service.api.resp.Resp;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * Created by LuoHaifeng on 2018/4/11 0011.
 * Email:496349136@qq.com
 */
public class ResponseObjectTransformer<T> implements ObservableTransformer<Resp<T>, Object> {

    @Override
    public ObservableSource<Object> apply(Observable<Resp<T>> upstream) {
        return upstream.map(new Function<Resp<T>, Object>() {
            @Override
            public Object apply(Resp<T> tResp) throws Exception {
                if (tResp.isSuccess()) {//成功
                    return tResp.getData() == null ? new Object() : tResp.getData();
                }
                throw new CodeException(tResp.getCode() + "", tResp.getMessage());
            }
        });
    }

    public static <T> ResponseObjectTransformer<T> create() {
        return new ResponseObjectTransformer<>();
    }
}
