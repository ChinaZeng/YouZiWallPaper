package com.youzi.youziwallpaper.app.mvp.presenters;

import com.youzi.framework.base.mvp.BasePresenter;
import com.youzi.youziwallpaper.app.mvp.contracts.FollowFragmentContract;

import javax.inject.Inject;

public class FollowFragmentPresenter extends BasePresenter<FollowFragmentContract.View> implements FollowFragmentContract.Presenter {
    @Inject
    public FollowFragmentPresenter() {
    }
}
