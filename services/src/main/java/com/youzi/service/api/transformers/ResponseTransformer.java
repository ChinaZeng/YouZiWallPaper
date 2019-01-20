package com.youzi.service.api.transformers;


import com.youzi.framework.common.error.CodeException;
import com.youzi.framework.common.error.EmptyDataException;
import com.youzi.service.api.resp.Resp;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * Created by LuoHaifeng on 2018/4/11 0011.
 * Email:496349136@qq.com
 */
public class ResponseTransformer<T> implements ObservableTransformer<Resp<T>, T> {
    private ResponseTransformer() {
    }

    @Override
    public ObservableSource<T> apply(Observable<Resp<T>> upstream) {
        return upstream.map(new Function<Resp<T>, T>() {
            @Override
                public T apply(Resp<T> tResp) throws Exception {
                if (tResp.isSuccess()) {//成功
                    return tResp.getData();
                } else if (tResp.getCode() == 400) {
                    throw new EmptyDataException();
                }
                throw new CodeException(tResp.getCode() + "", tResp.getMessage());
            }
        });
    }

    public static <T> ResponseTransformer<T> create() {
        return new ResponseTransformer<T>() {
        };
    }
}
