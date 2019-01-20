package com.youzi.framework.common.util.validate;

import android.annotation.SuppressLint;

import com.youzi.framework.common.Config;
import com.youzi.framework.common.error.ErrorHandlerManager;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;

/**
 * Created by LuoHaifeng on 2018/4/24 0024.
 * Email:496349136@qq.com
 */
public class QValidator {
    public static Observable<Boolean> validate(final OnValidate validate) {
        return Observable.defer(new Callable<ObservableSource<? extends Boolean>>() {
            @Override
            public ObservableSource<? extends Boolean> call() throws Exception {
                return Observable.create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                        validate.validate();
                        emitter.onNext(true);
                        emitter.onComplete();
                    }
                });
            }
        });
    }

    @SuppressLint("CheckResult")
    public static void validate(final OnValidate validate, Consumer<Boolean> success, Consumer<Throwable> error) {
        validate(validate)
                .subscribe(success, error);
    }

    public static void validate(final OnValidate validate, Consumer<Boolean> success) {
        validate(validate, success, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Config.getUiProvider().provideToast().showError(ErrorHandlerManager.getInstance().getErrorMessage(throwable));
            }
        });
    }

    public interface OnValidate {
        void validate() throws Exception;
    }
}
