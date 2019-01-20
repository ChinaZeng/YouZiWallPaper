package com.youzi.framework.common.util.transformers.rx;

import com.youzi.framework.common.Config;
import com.youzi.framework.common.error.ErrorHandlerManager;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by LuoHaifeng on 2017/12/26 0026.
 * Email:496349136@qq.com
 */

public class ErrorHandlerTransformer<T> implements Observable.Transformer<T, T> {
    @Override
    public Observable<T> call(Observable<T> upstream) {
        return upstream
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Config.getUiProvider().provideToast().showError(ErrorHandlerManager.getInstance().getErrorMessage(throwable));
                    }
                });
    }

    public static <T> ErrorHandlerTransformer<T> create() {
        return new ErrorHandlerTransformer<>();
    }
}
