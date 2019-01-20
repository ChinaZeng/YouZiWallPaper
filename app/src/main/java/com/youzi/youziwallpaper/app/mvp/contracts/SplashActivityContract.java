package com.youzi.youziwallpaper.app.mvp.contracts;


import com.youzi.framework.base.mvp.IBasePresenter;
import com.youzi.framework.base.mvp.IBaseView;

public interface SplashActivityContract {
    interface View extends IBaseView {
        void toMain();
    }

    interface Presenter extends IBasePresenter<View> {

    }
}
