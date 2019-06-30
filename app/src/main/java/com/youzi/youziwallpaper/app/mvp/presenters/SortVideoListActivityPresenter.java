package com.youzi.youziwallpaper.app.mvp.presenters;

import com.youzi.framework.base.mvp.BasePresenter;
import com.youzi.framework.common.util.transformers.rx2.SchedulersIoMainTransformer;
import com.youzi.service.api.RespObserver;
import com.youzi.service.api.apis.ClientApi;
import com.youzi.service.api.resp.ThemeBean;
import com.youzi.service.api.transformers.ListResponseTransformer;
import com.youzi.service.api.transformers.ResponseTransformer;
import com.youzi.youziwallpaper.app.mvp.contracts.SortVideoListActivityContract;

import java.util.List;

import javax.inject.Inject;

public class SortVideoListActivityPresenter extends BasePresenter<SortVideoListActivityContract.View> implements SortVideoListActivityContract.Presenter {
    @Inject
    ClientApi mClientApi;

    @Inject
    public SortVideoListActivityPresenter() {
    }

    @Override
    public void getData(String sortName) {
        //todo  接口更改
        mClientApi.getPageHomeThemeLst()
                .compose(new SchedulersIoMainTransformer<>())
                .compose(mView.bindLifecycle())
                .compose(ResponseTransformer.create())
                .compose(ListResponseTransformer.create())
                .subscribe(new RespObserver<List<ThemeBean>>() {
                    @Override
                    public void onSuccess(List<ThemeBean> data) {
                        mView.showList(data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showError();
                    }
                });
    }
}
