package com.youzi.youziwallpaper.app.mvp.presenters;

import com.youzi.framework.base.mvp.BasePresenter;
import com.youzi.youziwallpaper.app.mvp.contracts.EditPersonInfoActivityContract;

import javax.inject.Inject;

public class EditPersonInfoActivityPresenter extends BasePresenter<EditPersonInfoActivityContract.View> implements EditPersonInfoActivityContract.Presenter {
    @Inject
    public EditPersonInfoActivityPresenter() {
    }
}
