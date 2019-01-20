package com.youzi.youziwallpaper.app.mvp.presenters;

import com.youzi.framework.base.mvp.BasePresenter;
import com.youzi.framework.common.util.transformers.rx2.SchedulersIoMainTransformer;
import com.youzi.service.api.transformers.ResponseTransformer;
import com.youzi.youziwallpaper.app.mvp.contracts.SplashActivityContract;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class SplashActivityPresenter extends BasePresenter<SplashActivityContract.View> implements SplashActivityContract.Presenter {
    @Inject
    public SplashActivityPresenter() {
    }

    @Override
    public void init() {
        super.init();
        Observable
                .timer(2, TimeUnit.SECONDS)
                .compose(mView.bindLifecycle())
                .compose(SchedulersIoMainTransformer.create())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        mView.toMain();
                    }
                });
    }
}
