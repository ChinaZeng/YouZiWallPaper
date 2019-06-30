package com.youzi.youziwallpaper.app.mvp.contracts;

import com.youzi.framework.base.mvp.IBasePresenter;
import com.youzi.framework.base.mvp.IBaseView;
import com.youzi.service.api.resp.ThemeBean;

import java.util.List;

public interface SortVideoListActivityContract {
    interface View extends IBaseView {
        void showList(List<ThemeBean> list);
        void showError();
    }

    interface Presenter extends IBasePresenter<View> {

        void  getData(String sortName);
    }
}
