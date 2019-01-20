package com.youzi.youziwallpaper.app.mvp.presenters;

import com.youzi.framework.base.mvp.BasePresenter;
import com.youzi.youziwallpaper.app.mvp.contracts.VideoDetailActivityContract;

import javax.inject.Inject;

public class VideoDetailActivityPresenter extends BasePresenter<VideoDetailActivityContract.View> implements VideoDetailActivityContract.Presenter {
    @Inject
    public VideoDetailActivityPresenter() {
    }
}
