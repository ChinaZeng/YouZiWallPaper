package com.youzi.youziwallpaper.app.mvp.contracts;

import com.youzi.framework.base.mvp.IBasePresenter;
import com.youzi.framework.base.mvp.IBaseView;
import com.youzi.service.api.resp.ThemeBean;

import java.util.List;

public interface FollowFragmentContract {
    interface View extends IBaseView {
        void showList(int total,int page,List<ThemeBean> list);

        void loadError(int page);
    }

    interface Presenter extends IBasePresenter<View> {
        void getData(int page);
    }
}
