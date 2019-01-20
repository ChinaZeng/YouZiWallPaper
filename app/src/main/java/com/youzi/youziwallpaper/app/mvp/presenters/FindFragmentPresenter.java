package com.youzi.youziwallpaper.app.mvp.presenters;

import com.youzi.framework.base.mvp.BasePresenter;
import com.youzi.youziwallpaper.app.mvp.contracts.FindFragmentContract;

import javax.inject.Inject;

public class FindFragmentPresenter extends BasePresenter<FindFragmentContract.View> implements FindFragmentContract.Presenter {
    @Inject
    public FindFragmentPresenter() {
    }
}
