package com.youzi.youziwallpaper.app.mvp.presenters;

import com.youzi.framework.base.mvp.BasePresenter;
import com.youzi.framework.common.util.transformers.rx2.SchedulersIoMainTransformer;
import com.youzi.service.api.RespObserver;
import com.youzi.service.api.apis.ClientApi;
import com.youzi.service.api.resp.SortBean;
import com.youzi.service.api.resp.ThemeBean;
import com.youzi.service.api.transformers.ListResponseTransformer;
import com.youzi.service.api.transformers.ResponseTransformer;
import com.youzi.youziwallpaper.app.mvp.contracts.SortFragmentContract;

import java.util.List;

import javax.inject.Inject;

public class SortFragmentPresenter extends BasePresenter<SortFragmentContract.View> implements SortFragmentContract.Presenter {

    @Inject
    ClientApi mClientApi;
    @Inject
    public SortFragmentPresenter() {
    }

    @Override
    public void getData() {
        mClientApi.getClassifyLst()
                .compose(new SchedulersIoMainTransformer<>())
                .compose(mView.bindLifecycle())
                .compose(ResponseTransformer.create())
                .subscribe(new RespObserver<List<SortBean>>() {
                    @Override
                    public void onSuccess(List<SortBean> data) {
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
