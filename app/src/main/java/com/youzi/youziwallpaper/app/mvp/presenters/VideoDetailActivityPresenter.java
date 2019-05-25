package com.youzi.youziwallpaper.app.mvp.presenters;

import com.youzi.framework.base.mvp.BasePresenter;
import com.youzi.framework.common.util.login.LoginManager;
import com.youzi.service.api.RespObserver;
import com.youzi.service.api.apis.ClientApi;
import com.youzi.service.api.transformers.ResponseObjectTransformer;
import com.youzi.service.api.transformers.ResponseTransformer;
import com.youzi.youziwallpaper.app.bean.UserInfoBean;
import com.youzi.youziwallpaper.app.mvp.contracts.VideoDetailActivityContract;

import javax.inject.Inject;

public class VideoDetailActivityPresenter extends BasePresenter<VideoDetailActivityContract.View> implements VideoDetailActivityContract.Presenter {
    @Inject
    ClientApi mClientApi;

    @Inject
    public VideoDetailActivityPresenter() {
    }

    @Override
    public void follow(String home_theme_id) {
        if (!LoginManager.getInstance().isLogin()) return;

        UserInfoBean userInfoBean = LoginManager.getInstance().getLastLoginResult();
        mClientApi.insertDataFollow(userInfoBean.token, home_theme_id)
                .compose(mView.newDialogLoadingTransformer())
                .compose(ResponseObjectTransformer.create())
                .subscribe(new RespObserver<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        mView.followSuccess();
                    }
                });
    }

    @Override
    public void collection(String home_theme_id) {
        if (!LoginManager.getInstance().isLogin()) return;

        UserInfoBean userInfoBean = LoginManager.getInstance().getLastLoginResult();
        mClientApi.insertDataCollection(userInfoBean.token, home_theme_id)
                .compose(mView.newDialogLoadingTransformer())
                .compose(ResponseObjectTransformer.create())
                .subscribe(new RespObserver<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        mView.collectionSuccess();
                    }
                });
    }
}
