package com.youzi.youziwallpaper.app.mvp.presenters;

import com.youzi.framework.base.mvp.BasePresenter;
import com.youzi.framework.common.util.login.LoginManager;
import com.youzi.service.api.RespObserver;
import com.youzi.service.api.apis.ClientApi;
import com.youzi.service.api.transformers.BooleanTransformer;
import com.youzi.service.api.transformers.ResponseTransformer;
import com.youzi.youziwallpaper.app.bean.UserInfoBean;
import com.youzi.youziwallpaper.app.mvp.contracts.MainActivityContract;

import javax.inject.Inject;

public class MainActivityPresenter extends BasePresenter<MainActivityContract.View> implements MainActivityContract.Presenter {
    @Inject
    ClientApi mClientApi;

    @Inject
    public MainActivityPresenter() {
    }

    @Override
    public void checkToken() {
        if(!LoginManager.getInstance().isLogin()){
            return;
        }

        UserInfoBean userInfoBean= LoginManager.getInstance().getLastLoginResult();
        mClientApi.checkToken(userInfoBean.token)
                .compose(mView.newDialogLoadingTransformer())
                .compose(BooleanTransformer.create())
                .subscribe(new RespObserver<Boolean>(){
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        super.onSuccess(aBoolean);
                    }
                });

    }


}
