package com.youzi.framework.common.util.transformers.rx2;

import com.youzi.framework.common.compat.rx2.ILifeCycleProvider;
import com.youzi.framework.common.error.EmptyDataException;
import com.youzi.framework.common.error.ErrorHandlerManager;
import com.youzi.framework.common.ui.ILoadingHelper;

import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by LuoHaifeng on 2017/12/26 0026.
 * Email:496349136@qq.com
 */

public class ILoadingTransformer<T> implements ObservableTransformer<T, T> {
    private ILoadingHelper loadingHelper;
    private ILifeCycleProvider lifeCycleProvider;
    private boolean isErrored = false;

    public ILoadingTransformer(ILoadingHelper loadingHelper) {
        this.loadingHelper = loadingHelper;
    }

    public ILoadingTransformer(ILoadingHelper loadingHelper, ILifeCycleProvider lifeCycleProvider) {
        this.loadingHelper = loadingHelper;
        this.lifeCycleProvider = lifeCycleProvider;
    }

    @Override
    public ObservableSource<T> apply(io.reactivex.Observable<T> upstream) {
        if (lifeCycleProvider != null) {
            upstream = upstream.compose(lifeCycleProvider.<T>bindLifecycle());
        }
        return upstream
                .compose(new SchedulersIoMainTransformer<T>())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(final Disposable disposable) throws Exception {
                        if (loadingHelper != null) {
                            loadingHelper.showLoading();
                        }
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (throwable instanceof EmptyDataException) {
                            return;
                        }
                        isErrored = true;
                        if (loadingHelper != null) {
                            ErrorHandlerManager.getInstance().switchErrorLayout(throwable, loadingHelper);
                        }
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (loadingHelper != null && loadingHelper.getState() == ILoadingHelper.State.LOADING) {
                            loadingHelper.restore();
                        }
                    }
                });
    }
}
