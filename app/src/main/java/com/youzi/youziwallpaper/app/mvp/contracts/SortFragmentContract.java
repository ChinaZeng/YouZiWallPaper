package com.youzi.youziwallpaper.app.mvp.contracts;

import com.youzi.framework.base.mvp.IBasePresenter;
import com.youzi.framework.base.mvp.IBaseView;
import com.youzi.service.api.resp.SortBean;

import java.util.List;

public interface SortFragmentContract {
    interface View extends IBaseView {
        void showList(List<SortBean> list);

        void showError();
    }

    interface Presenter extends IBasePresenter<View> {
        void getData();
    }
}
