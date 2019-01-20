package com.youzi.framework.common.util.transformers.rx;

import android.app.DialogFragment;
import android.app.FragmentManager;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by LuoHaifeng on 2017/12/26 0026.
 * Email:496349136@qq.com
 */

public class FragmentDialogTransformer<T> implements Observable.Transformer<T, T> {
    private DialogFragment dialog;
    private FragmentManager fragmentManager;

    public FragmentDialogTransformer(DialogFragment dialog, FragmentManager fragmentManager) {
        this.dialog = dialog;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public Observable<T> call(final Observable<T> upstream) {
        return upstream
                .compose(new SchedulersIoMainTransformer<T>())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (dialog != null) {
                            dialog.show(fragmentManager, "transformerLoadingDialog");
                        }
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<T>() {
                    @Override
                    public void call(T t) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                })
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                });
    }
}
