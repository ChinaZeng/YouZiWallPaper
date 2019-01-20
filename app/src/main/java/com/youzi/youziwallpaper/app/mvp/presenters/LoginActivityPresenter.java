package com.youzi.youziwallpaper.app.mvp.presenters;

import com.youzi.framework.base.mvp.BasePresenter;
import com.youzi.service.api.apis.ClientApi;
import com.youzi.youziwallpaper.app.mvp.contracts.LoginActivityContract;

import javax.inject.Inject;

/**
 * Created by zzw on 2019/1/11.
 * 描述:
 */
public class LoginActivityPresenter extends BasePresenter<LoginActivityContract.View> implements LoginActivityContract.Presenter {

    @Inject
    ClientApi mClientApi;

    @Inject
    public LoginActivityPresenter() {
    }


}
