package com.youzi.youziwallpaper.app.mvp.presenters;

import com.youzi.framework.base.mvp.BasePresenter;
import com.youzi.framework.common.util.login.LoginManager;
import com.youzi.framework.common.util.transformers.rx2.SchedulersIoMainTransformer;
import com.youzi.service.api.RespObserver;
import com.youzi.service.api.apis.ClientApi;
import com.youzi.service.api.resp.ThemeBean;
import com.youzi.service.api.transformers.ListResponseTransformer;
import com.youzi.service.api.transformers.ResponseTransformer;
import com.youzi.youziwallpaper.app.bean.UserInfoBean;
import com.youzi.youziwallpaper.app.mvp.contracts.RecommendFragmentContract;

import java.util.List;

import javax.inject.Inject;

public class RecommendFragmentPresenter extends BasePresenter<RecommendFragmentContract.View> implements RecommendFragmentContract.Presenter {

    @Inject
    ClientApi mClientApi;

    @Inject
    public RecommendFragmentPresenter() {
    }


    @Override
    public void getData(int nowPage) {
        String token = null;
        if (!LoginManager.getInstance().isLogin()) {
            UserInfoBean loginResult = LoginManager.getInstance().getLastLoginResult();
            token= loginResult.token;
        }

        mClientApi.getPageHomeThemeLst(10,nowPage,
                token,
                null,
                null,
                null
        )
                .compose(new SchedulersIoMainTransformer<>())
                .compose(mView.bindLifecycle())
                .compose(ResponseTransformer.create())
                .compose(ListResponseTransformer.create())
                .subscribe(new RespObserver<List<ThemeBean>>() {
                    @Override
                    public void onSuccess(List<ThemeBean> data) {
                        mView.showList(data,nowPage);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showError();
                    }
                });
    }
}
