package com.youzi.youziwallpaper.app.mvp.presenters;

import com.youzi.framework.base.mvp.BasePresenter;
import com.youzi.youziwallpaper.app.mvp.contracts.SortVideoListActivityContract;

import javax.inject.Inject;

public class SortVideoListActivityPresenter extends BasePresenter<SortVideoListActivityContract.View> implements SortVideoListActivityContract.Presenter {
    @Inject
    public SortVideoListActivityPresenter() {
    }
}
