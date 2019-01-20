package com.youzi.framework.common.util.transformers.rx2;

import com.youzi.framework.common.Config;
import com.youzi.framework.common.error.EmptyDataException;
import com.youzi.framework.common.error.ErrorHandlerManager;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by LuoHaifeng on 2017/12/26 0026.
 * Email:496349136@qq.com
 */

public class ErrorHandlerTransformer<T> implements ObservableTransformer<T, T> {
    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        return upstream
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if(throwable instanceof EmptyDataException){
                            return;
                        }
                        if(throwable != null){
                            throwable.printStackTrace();
                        }
                        Config.getUiProvider().provideToast().showError(ErrorHandlerManager.getInstance().getErrorMessage(throwable));
                    }
                });
    }

    public static <T> ErrorHandlerTransformer<T> create() {
        return new ErrorHandlerTransformer<>();
    }
}
