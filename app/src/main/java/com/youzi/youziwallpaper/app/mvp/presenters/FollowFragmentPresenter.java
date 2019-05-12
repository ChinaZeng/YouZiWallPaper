package com.youzi.youziwallpaper.app.mvp.presenters;

import com.youzi.framework.base.mvp.BasePresenter;
import com.youzi.framework.common.util.login.LoginManager;
import com.youzi.framework.common.util.transformers.rx2.SchedulersIoMainTransformer;
import com.youzi.service.api.RespObserver;
import com.youzi.service.api.apis.ClientApi;
import com.youzi.service.api.resp.ListResp;
import com.youzi.service.api.resp.ThemeBean;
import com.youzi.service.api.transformers.ListResponseTransformer;
import com.youzi.service.api.transformers.ResponseTransformer;
import com.youzi.youziwallpaper.app.bean.UserInfoBean;
import com.youzi.youziwallpaper.app.mvp.contracts.FollowFragmentContract;

import java.util.List;

import javax.inject.Inject;

import static com.youzi.youziwallpaper.app.Contancs.PAGE_NUM;

public class FollowFragmentPresenter extends BasePresenter<FollowFragmentContract.View> implements FollowFragmentContract.Presenter {


    @Inject
    ClientApi mClientApi;


    @Inject
    public FollowFragmentPresenter() {
    }

    @Override
    public void getData(int page) {

        if (!LoginManager.getInstance().isLogin()) {
            return;
        }
        UserInfoBean userInfoBean = LoginManager.getInstance().getLastLoginResult();
        mClientApi.getPageFollowLst(userInfoBean.token, page, PAGE_NUM)
                .compose(new SchedulersIoMainTransformer<>())
                .compose(mView.bindLifecycle())
                .compose(ResponseTransformer.create())
                .subscribe(new RespObserver<ListResp<ThemeBean>>() {
                    @Override
                    public void onSuccess(ListResp<ThemeBean> data) {
                        mView.showList(data.getTotal(), page, data.getRows());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.loadError(page);
                    }
                });
    }


}
