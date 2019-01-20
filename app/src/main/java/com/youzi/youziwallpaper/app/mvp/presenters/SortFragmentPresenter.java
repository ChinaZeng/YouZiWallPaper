package com.youzi.youziwallpaper.app.mvp.presenters;

import com.youzi.framework.base.mvp.BasePresenter;
import com.youzi.youziwallpaper.app.mvp.contracts.SortFragmentContract;

import javax.inject.Inject;

public class SortFragmentPresenter extends BasePresenter<SortFragmentContract.View> implements SortFragmentContract.Presenter {
    @Inject
    public SortFragmentPresenter() {
    }
}
