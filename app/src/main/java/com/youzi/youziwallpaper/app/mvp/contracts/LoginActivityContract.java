package com.youzi.youziwallpaper.app.mvp.contracts;


import android.app.Activity;
import android.content.Context;

import com.youzi.framework.base.mvp.IBasePresenter;
import com.youzi.framework.base.mvp.IBaseView;

public interface LoginActivityContract {
    interface View extends IBaseView {
        void toMain();
    }

    interface Presenter extends IBasePresenter<View> {
        void qqLogin(Activity activity);
    }
}
