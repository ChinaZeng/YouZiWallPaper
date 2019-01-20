package com.youzi.youziwallpaper.app.mvp.presenters;

import com.youzi.framework.base.mvp.BasePresenter;
import com.youzi.youziwallpaper.app.mvp.contracts.MainActivityContract;

import javax.inject.Inject;

public class MainActivityPresenter extends BasePresenter<MainActivityContract.View> implements MainActivityContract.Presenter {
    @Inject
    public MainActivityPresenter() {
    }
}
