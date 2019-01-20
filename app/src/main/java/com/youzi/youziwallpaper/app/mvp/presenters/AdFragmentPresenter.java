package com.youzi.youziwallpaper.app.mvp.presenters;

import com.youzi.framework.base.mvp.BasePresenter;
import com.youzi.youziwallpaper.app.mvp.contracts.AdFragmentContract;

import javax.inject.Inject;

public class AdFragmentPresenter extends BasePresenter<AdFragmentContract.View> implements AdFragmentContract.Presenter {
    @Inject
    public AdFragmentPresenter() {
    }
}
