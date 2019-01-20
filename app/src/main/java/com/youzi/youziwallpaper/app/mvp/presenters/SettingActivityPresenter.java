package com.youzi.youziwallpaper.app.mvp.presenters;

import com.youzi.framework.base.mvp.BasePresenter;
import com.youzi.youziwallpaper.app.mvp.contracts.SettingActivityContract;

import javax.inject.Inject;

public class SettingActivityPresenter extends BasePresenter<SettingActivityContract.View> implements SettingActivityContract.Presenter {
    @Inject
    public SettingActivityPresenter() {
    }
}
