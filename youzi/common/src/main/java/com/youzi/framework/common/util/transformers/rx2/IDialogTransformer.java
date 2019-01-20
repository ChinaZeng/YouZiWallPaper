package com.youzi.framework.common.util.transformers.rx2;

import com.youzi.framework.common.compat.rx2.ILifeCycleProvider;
import com.youzi.framework.common.ui.dialog.OnCancelListener;
import com.youzi.framework.common.ui.dialog.i.IDialog;

import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by LuoHaifeng on 2017/12/26 0026.
 * Email:496349136@qq.com
 */

public class IDialogTransformer<T> implements ObservableTransformer<T, T> {
    private IDialog dialog;
    private ILifeCycleProvider lifeCycleProvider;

    public IDialogTransformer(IDialog dialog) {
        this.dialog = dialog;
    }

    public IDialogTransformer(IDialog dialog, ILifeCycleProvider lifeCycleProvider) {
        this.dialog = dialog;
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
                        if (dialog != null) {
                            dialog.show();
                            dialog.setOnCancelListener(new OnCancelListener() {
                                @Override
                                public void onCancel() {
                                    if (!disposable.isDisposed()) {
                                        disposable.dispose();
                                    }
                                }
                            });
                        }
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });
    }
}
