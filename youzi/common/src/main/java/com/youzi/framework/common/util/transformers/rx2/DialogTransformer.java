package com.youzi.framework.common.util.transformers.rx2;

import android.app.Dialog;
import android.content.DialogInterface;

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

public class DialogTransformer<T> implements ObservableTransformer<T, T> {
    private Dialog dialog;

    public DialogTransformer(Dialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public ObservableSource<T> apply(io.reactivex.Observable<T> upstream) {
        return upstream
                .compose(new SchedulersIoMainTransformer<T>())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(final Disposable disposable) throws Exception {
                        if (dialog != null) {
                            dialog.show();
                            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialogInterface) {
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
