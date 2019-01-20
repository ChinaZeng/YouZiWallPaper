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
public class ResponseTransformerV2<T> implements ObservableTransformer<Resp<T>, Resp<T>> {

    @Override
    public ObservableSource<Resp<T>> apply(Observable<Resp<T>> upstream) {
        return upstream.map(new Function<Resp<T>, Resp<T>>() {
            @Override
            public Resp<T> apply(Resp<T> tResp) throws Exception {
                if (tResp.isSuccess() || tResp.getCode() == Resp.CODE_400) {//成功
                    return tResp;
                }
                throw new CodeException(tResp.getCode() + "", tResp.getMessage());
            }
        });
    }

    public static <T> ResponseTransformerV2<T> create() {
        return new ResponseTransformerV2<>();
    }
}
