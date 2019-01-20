package com.youzi.youziwallpaper.app.mvp.contracts;

import com.youzi.framework.base.mvp.IBasePagingView;
import com.youzi.framework.base.mvp.IBasePresenter;
import com.youzi.framework.base.mvp.IBaseView;

public interface VideoListFragmentContract {
    interface View extends IBasePagingView {

    }

    interface Presenter extends IBasePresenter<View> {

    }
}
