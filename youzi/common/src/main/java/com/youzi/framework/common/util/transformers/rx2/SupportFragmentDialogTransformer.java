package com.youzi.framework.common.util.transformers.rx2;

import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import io.reactivex.Observable;
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

public class SupportFragmentDialogTransformer<T> implements ObservableTransformer<T, T> {
    private DialogFragment dialog;
    private FragmentManager fragmentManager;

    public SupportFragmentDialogTransformer(DialogFragment dialog, FragmentManager fragmentManager) {
        this.dialog = dialog;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        return upstream
                .compose(new SchedulersIoMainTransformer<T>())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(final Disposable disposable) throws Exception {
                        if (dialog != null) {
                            dialog.getDialog().setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialogInterface) {
                                    if (!disposable.isDisposed()) {
                                        disposable.dispose();
                                    }
                                }
                            });
                            dialog.show(fragmentManager, "transformerLoadingDialog");
                        }
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                });
    }
}
