package com.youzi.framework.base.mvp;


import com.youzi.framework.common.util.paging.v2.Paging;

public abstract class SimplePagingPresenter<T,V extends IBasePagingView> extends BasePresenter<V>{
    protected Paging<T> mPaging;

    @Override
    public void refreshContent() {
        if(mPaging != null){
            mPaging.startLoading();
        }
    }

    @Override
    public void init() {
        super.init();
        mPaging = configPagingImpl(mView.providePagingV2());
    }

    protected abstract Paging<T> configPagingImpl(Paging<T> paging);
}
