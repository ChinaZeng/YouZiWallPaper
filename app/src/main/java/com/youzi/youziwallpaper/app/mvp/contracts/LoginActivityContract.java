package com.youzi.youziwallpaper.app.mvp.contracts;


import com.youzi.framework.base.mvp.IBasePresenter;
import com.youzi.framework.base.mvp.IBaseView;

public interface LoginActivityContract {
    interface View extends IBaseView {
        void toMain();
    }

    interface Presenter extends IBasePresenter<View> {

        void saveQQLoginInfo(Object response,String openId);
        void saveWXLoginInfo(Object response);
    }
}
