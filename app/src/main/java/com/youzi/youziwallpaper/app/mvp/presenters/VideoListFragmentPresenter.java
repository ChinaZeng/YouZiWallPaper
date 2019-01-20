package com.youzi.youziwallpaper.app.mvp.presenters;

import com.youzi.framework.base.mvp.SimplePagingPresenter;
import com.youzi.framework.common.util.paging.v2.Paging;
import com.youzi.youziwallpaper.app.mvp.contracts.VideoListFragmentContract;

import javax.inject.Inject;

public class VideoListFragmentPresenter extends SimplePagingPresenter<String,VideoListFragmentContract.View> implements VideoListFragmentContract.Presenter {
    @Inject
    public VideoListFragmentPresenter() {
    }


    @Override
    protected Paging<String> configPagingImpl(Paging<String> paging) {
        return null;
    }
}
