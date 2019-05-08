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
public class BooleanTransformer implements ObservableTransformer<Resp, Boolean> {
    private BooleanTransformer() {
    }

    @Override
    public ObservableSource<Boolean> apply(Observable<Resp> upstream) {
        return upstream.map(new Function<Resp, Boolean>() {
            @Override
            public Boolean apply(Resp resp) throws Exception {
                return resp.isSuccess();
            }
        });
    }

    public static BooleanTransformer create() {
        return new BooleanTransformer();
    }
}
