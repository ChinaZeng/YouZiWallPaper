package com.youzi.youziwallpaper.app.mvp.contracts;

import com.youzi.framework.base.mvp.IBasePresenter;
import com.youzi.framework.base.mvp.IBaseView;

public interface VideoDetailActivityContract {
    interface View extends IBaseView {

        void followSuccess();
        void collectionSuccess();
    }

    interface Presenter extends IBasePresenter<View> {

        void follow(String home_theme_id);
        void collection(String home_theme_id);
    }
}
