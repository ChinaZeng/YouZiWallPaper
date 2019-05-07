package com.youzi.service.api;


import com.youzi.framework.common.Config;
import com.youzi.framework.common.error.EmptyDataException;
import com.youzi.framework.common.error.ErrorHandlerManager;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by zzw on 2018/4/12
 */
public class RespObserver<T> implements Observer<T> {

    public RespObserver(boolean showErr) {
        this.showErr = showErr;
    }

    public RespObserver() {

    }

    private boolean showErr = true;

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public final void onNext(T t) {
        try {
            onSuccess(t);
        } catch (Exception e) {
            onError(e);
        }
    }

    public void onSuccess(T t) {

    }

    @Override
    public void onError(Throwable e) {
        if(e instanceof EmptyDataException){
            return;
        }
        e.printStackTrace();
        if (showErr) {
            Config.getUiProvider().provideToast().showError(ErrorHandlerManager.getInstance().getErrorMessage(e));
        }
    }

    @Override
    public void onComplete() {

    }
}
